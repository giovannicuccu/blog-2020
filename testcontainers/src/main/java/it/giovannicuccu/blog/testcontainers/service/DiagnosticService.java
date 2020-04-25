package it.giovannicuccu.blog.testcontainers.service;

import java.util.List;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.zaxxer.hikari.HikariDataSource;

import it.giovannicuccu.blog.testcontainers.dao.DiagnosticDao;
import it.giovannicuccu.blog.testcontainers.model.DiagnosticEvent;

public class DiagnosticService {
	
	public static final String DB_DIAG_PASSWORD = "db.diag.password";
	public static final String DB_DIAG_USERNAME = "db.diag.username";
	public static final String DB_DIAG_JDBC_URL = "db.diag.jdbc.url";
	private SqlSessionFactory sqlSessionFactory;
	HikariDataSource dataSource = new HikariDataSource();
	
	public DiagnosticService() {
		dataSource.setJdbcUrl(System.getProperty(DB_DIAG_JDBC_URL));
		dataSource.setUsername(System.getProperty(DB_DIAG_USERNAME));
		dataSource.setPassword(System.getProperty(DB_DIAG_PASSWORD));
		dataSource.setMaximumPoolSize(5);
		dataSource.setAutoCommit(false);
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Environment environment = new Environment("development", transactionFactory, dataSource);
		Configuration configuration = new Configuration(environment);
		configuration.addMapper(DiagnosticDao.class);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
	}
	
	public void shutdown() {
		dataSource.close();
	}
	
	public void insertEvent(DiagnosticEvent diagnosticEvent) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			 DiagnosticDao mapper = session.getMapper(DiagnosticDao.class);
			 mapper.insertEvent(diagnosticEvent);
			 session.commit();
		}
	}
	
	public List<DiagnosticEvent> loadAllEvents() {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			 DiagnosticDao mapper = session.getMapper(DiagnosticDao.class);
			 return mapper.listAll();
		}
	}
	

}
