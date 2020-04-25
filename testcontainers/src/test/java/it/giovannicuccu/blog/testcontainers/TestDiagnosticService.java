package it.giovannicuccu.blog.testcontainers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.utility.MountableFile;

import it.giovannicuccu.blog.testcontainers.model.DiagnosticEvent;
import it.giovannicuccu.blog.testcontainers.service.DiagnosticService;

public class TestDiagnosticService {
	
	

	private static final Logger logger=LoggerFactory.getLogger(TestDiagnosticService.class.getName());
	
	private static final String DOCKER_ENTRYPOINT_INITDB_D = "/docker-entrypoint-initdb.d/";
	
	private static final String CREATE_USER_SCRIPT="01_create_user.sql";
	private static final String CREATE_TABLE_SCRIPT="02_create_table.sql";
	private static final String SQL="sql/";
	
	static {
		final String TARGET="target/classes/"+SQL;
		File targetDir=new File(TARGET);
		if (!targetDir.exists()) {
			targetDir.mkdirs();
		}
		File targetCreateUser=new File(TARGET+CREATE_USER_SCRIPT);
		if (targetCreateUser.exists()) {
			targetCreateUser.delete();
		}
		File targetCreateTable=new File(TARGET+CREATE_TABLE_SCRIPT);
		if (targetCreateTable.exists()) {
			targetCreateTable.delete();
		}
		try {
			Files.copy(Paths.get(SQL+CREATE_USER_SCRIPT), Paths.get(TARGET+CREATE_USER_SCRIPT), StandardCopyOption.REPLACE_EXISTING);
			Files.copy(Paths.get(SQL+CREATE_TABLE_SCRIPT), Paths.get(TARGET+CREATE_TABLE_SCRIPT),StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Rule
    public PostgreSQLContainer postgreSQL = new PostgreSQLContainer<>()
    	.withCopyFileToContainer(MountableFile.forClasspathResource(SQL+CREATE_TABLE_SCRIPT),DOCKER_ENTRYPOINT_INITDB_D+CREATE_TABLE_SCRIPT)
    	.withCopyFileToContainer(MountableFile.forClasspathResource(SQL+CREATE_USER_SCRIPT),DOCKER_ENTRYPOINT_INITDB_D+CREATE_USER_SCRIPT)
    	;
	
	
	
	public TestDiagnosticService() throws Exception {
		
		
	}
	
	
	@Before
	public void setup() {
		System.setProperty(DiagnosticService.DB_DIAG_JDBC_URL, postgreSQL.getJdbcUrl());
		System.setProperty(DiagnosticService.DB_DIAG_USERNAME, "diag");
		System.setProperty(DiagnosticService.DB_DIAG_PASSWORD, "diag");
	}
	
	@Test
	public void testDiagnosticService() {
		DiagnosticService diagnosticService= new DiagnosticService();
		DiagnosticEvent diagnosticEvent=new DiagnosticEvent();
		diagnosticEvent.setElapsedMillis(100);
		diagnosticEvent.setName("test");
		diagnosticEvent.setResult("OK");
		diagnosticEvent.setStart(LocalDateTime.now());
		diagnosticEvent.setType("HTTP_SERVICE");
		diagnosticService.insertEvent(diagnosticEvent);
		Assert.assertEquals(1,diagnosticService.loadAllEvents().size());
		diagnosticService.shutdown();
	}
	
	@Test
	public void testFileBeatKafka() {
		try (
		Network network = Network.newNetwork();
		KafkaContainer  kafkaContainer=new KafkaContainer()
				.withNetwork(network)
                .withNetworkAliases("kafka");
		
		GenericContainer filebeatContainer=new GenericContainer<>(
				new ImageFromDockerfile()
				.withDockerfile(Paths.get("src/test/resources/filebeat/Dockerfile"))).withNetwork(network);
		) {
			kafkaContainer.start();
			filebeatContainer.start();
		}
	}
	
	@Test
	public void testDockerNetwork() throws Exception {
		try (
		        Network network = Network.newNetwork();

		        GenericContainer foo = new GenericContainer()
		                .withNetwork(network)
		                .withNetworkAliases("foo")
		                .withCommand("/bin/sh", "-c", "while true ; do printf 'HTTP/1.1 200 OK\\n\\nyay' | nc -l -p 8080; done");

		        GenericContainer bar = new GenericContainer()
		                .withNetwork(network)
		                .withCommand("top")
		) {
		    foo.start();
		    bar.start();

		    String response = bar.execInContainer("wget", "-O", "-", "http://foo:8080").getStdout();
		    Assert.assertEquals("received response", "yay", response);
		}		
	}
}
