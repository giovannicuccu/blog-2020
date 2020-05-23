package it.giovannicuccu.blog.testcontainers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.utility.MountableFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.giovannicuccu.blog.testcontainers.model.DiagnosticEvent;
import it.giovannicuccu.blog.testcontainers.service.DiagnosticService;

public class TestDiagnosticService2 {

	private static final Logger logger = LoggerFactory.getLogger(TestDiagnosticService2.class.getName());
	private static final Logger loggerFileBeat = LoggerFactory.getLogger("filebeat");

	private static final String DOCKER_ENTRYPOINT_INITDB_D = "/docker-entrypoint-initdb.d/";

	private static final String CREATE_USER_SCRIPT = "01_create_user.sql";
	private static final String CREATE_TABLE_SCRIPT = "02_create_table.sql";
	private static final String SQL = "sql/";

	private ObjectMapper objectMapper = new ObjectMapper();
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

	@Before
	public void setup() {
		System.setProperty(DiagnosticService.DB_DIAG_USERNAME, "diag");
		System.setProperty(DiagnosticService.DB_DIAG_PASSWORD, "diag");
	}

	@Test
	public void testFileBeatKafka() throws Exception {
		final int NUM_MESSAGES=10;
		for (int i = 0; i < NUM_MESSAGES; i++) {
			loggerFileBeat.debug("|name={}|type={}|status={}|start_ts={}|ela={}|", "name" + i, "HTTP_SERVICE", "OK",
					formatter.format(LocalDateTime.now()), i + 100);
		}
		logger.info("filebeat log written");
		final String TARGET = "target/classes/" + SQL;
		File targetDir = new File(TARGET);
		if (!targetDir.exists()) {
			targetDir.mkdirs();
		}
		File targetCreateUser = new File(TARGET + CREATE_USER_SCRIPT);
		if (targetCreateUser.exists()) {
			targetCreateUser.delete();
		}
		File targetCreateTable = new File(TARGET + CREATE_TABLE_SCRIPT);
		if (targetCreateTable.exists()) {
			targetCreateTable.delete();
		}
		try {
			Files.copy(Paths.get(SQL + CREATE_USER_SCRIPT), Paths.get(TARGET + CREATE_USER_SCRIPT),
					StandardCopyOption.REPLACE_EXISTING);
			Files.copy(Paths.get(SQL + CREATE_TABLE_SCRIPT), Paths.get(TARGET + CREATE_TABLE_SCRIPT),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (

				PostgreSQLContainer postgreSQL = new PostgreSQLContainer<>()
						.withCopyFileToContainer(MountableFile.forClasspathResource(SQL + CREATE_TABLE_SCRIPT),
								DOCKER_ENTRYPOINT_INITDB_D + CREATE_TABLE_SCRIPT)
						.withCopyFileToContainer(MountableFile.forClasspathResource(SQL + CREATE_USER_SCRIPT),
								DOCKER_ENTRYPOINT_INITDB_D + CREATE_USER_SCRIPT);
				Network network = Network.newNetwork();
				KafkaContainer kafkaContainer = new KafkaContainer().withNetwork(network).withNetworkAliases("kafka");

				GenericContainer filebeatContainer = new GenericContainer<>(
						new ImageFromDockerfile().withDockerfile(Paths.get("src/test/resources/filebeat/Dockerfile")))
								.withNetwork(network)
								.withCopyFileToContainer(MountableFile.forClasspathResource("filebeat/diagnostic.log"),
										"/var/log/app/diagnostic.log");
/*
 * La seguente forma è alternativa a quella sopra, è un altro modo di fare
 * la stessa cosa, in un caso si usa un docker file custom, nell'altro si monta solo la conf di filebeat
 */
//				GenericContainer filebeatContainer = new GenericContainer<>(
//						"docker.elastic.co/beats/filebeat-oss:7.6.2")
//								.withNetwork(network)
//								.withClasspathResourceMapping("filebeat/filebeat.yml",
//	                                      "/usr/share/filebeat/filebeat.yml",
//	                                      BindMode.READ_ONLY)
//								.withCopyFileToContainer(MountableFile.forClasspathResource("filebeat/diagnostic.log"),
//										"/var/log/app/diagnostic.log");
				
				) {
			kafkaContainer.start();
			filebeatContainer.start();
			postgreSQL.start();
			System.setProperty(DiagnosticService.DB_DIAG_JDBC_URL, postgreSQL.getJdbcUrl());
			DiagnosticService diagnosticService = new DiagnosticService();
			Properties props = new Properties();
			props.setProperty("bootstrap.servers", kafkaContainer.getBootstrapServers());
			props.setProperty("group.id", "test");
			props.setProperty("enable.auto.commit", "true");
			props.setProperty("auto.commit.interval.ms", "1000");
			props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
			props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
			consumer.subscribe(Arrays.asList("diagnostic"));
			int tries = 10;
			int i = 0;
			while (i < tries) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
				if (records.count() == 0) {
					i++;
				}
				for (ConsumerRecord<String, String> record : records) {
					logger.info("offset = {}, key = {}, value = {}", record.offset(), record.key(), record.value());
					Map<String, Object> recordAsMap = objectMapper.readValue(record.value(),
							new TypeReference<HashMap<String, Object>>() {
							});
					logger.info("message={}", recordAsMap.get("message"));
					DiagnosticEvent diagnosticEvent = parseEventFromLog(recordAsMap.get("message").toString());
					diagnosticService.insertEvent(diagnosticEvent);

				}
			}
			consumer.close();
			Assert.assertEquals(NUM_MESSAGES, diagnosticService.loadAllEvents().size());
		}
	}

	private DiagnosticEvent parseEventFromLog(String messageLog) {
		int startIndex = messageLog.indexOf("|");
		int endIndex = messageLog.lastIndexOf("|");
		if (endIndex > startIndex) {
			String message = messageLog.substring(startIndex + 1, endIndex - 1);
			String[] parts = message.split("\\|");
			logger.debug("parts of {} are {}", message, parts.length);
			if (parts.length == 5) {
				String name = getValue(parts[0]);
				String type = getValue(parts[1]);
				String status = getValue(parts[2]);
				String startTsStr = getValue(parts[3]);
				String elaStr = getValue(parts[4]);
				DiagnosticEvent diagnosticEvent = new DiagnosticEvent();
				diagnosticEvent.setName(name);
				diagnosticEvent.setType(type);
				diagnosticEvent.setResult(status);
				diagnosticEvent.setStart(LocalDateTime.parse(startTsStr, formatter));
				diagnosticEvent.setElapsedMillis(Integer.parseInt(elaStr));
				logger.debug("returning {}", diagnosticEvent);
				return diagnosticEvent;
			}
		}
		return null;
	}

	private String getValue(String part) {
		String[] parts = part.split("=");
		if (parts.length == 2) {
			return parts[1];
		}
		return "";
	}

}
