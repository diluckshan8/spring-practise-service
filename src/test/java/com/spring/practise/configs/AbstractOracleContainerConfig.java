package com.spring.practise.configs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.oracle.OracleContainer;

/**
 * Abstract class that contains all configurations required to use embedded containers
 *
 * @author Diiukshan Puviraj
 */
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@EnableAutoConfiguration
@Testcontainers
public abstract class AbstractOracleContainerConfig {

  @Container
  public static OracleContainer oracle = new OracleContainer("gvenzl/oracle-free:slim-faststart")
      .withDatabaseName("testDB")
      .withUsername("testUser")
      .withPassword("testPassword")
      .withExposedPorts(1521)
      .withStartupTimeout(Duration.ofMinutes(5));

  public static Connection connection;

  @BeforeAll
  static void setUp() throws SQLException, ClassNotFoundException {
    if (!oracle.isCreated()) {
      oracle.start();
    }
    connection = DriverManager.getConnection(oracle.getJdbcUrl(), oracle.getUsername(), oracle.getPassword());

  }

  @AfterAll
  static void tearDown() throws SQLException {
    if (connection != null && !connection.isClosed()) {
      connection.close();
    }
    if (oracle.isRunning()) {
      oracle.stop();
    }
  }

}






