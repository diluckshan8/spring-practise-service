package com.spring.practise.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.spring.practise.configs.AbstractOracleContainerConfig;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
/**
 * Integration test for Oracle Database implementation.
 */
class OracleContainerTest extends AbstractOracleContainerConfig {

    @Test
    void testDatabaseConnection() throws SQLException {
        assertNotNull(connection);
        assertFalse(connection.isClosed());
    }

    @Test
    void testDatabaseQueryExecution() throws SQLException {
        try (Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT 1 FROM dual")) {
            assertTrue(resultSet.next());
            assertEquals(1, resultSet.getInt(1));
        }
    }

    @Test
    void testInvalidCredentials() {
        assertThrows(SQLException.class, () -> {
            DriverManager.getConnection(oracle.getJdbcUrl(), "wrongUser", "wrongPassword");
        });
    }

    @Test
    void testDatabaseStartupTime() {
        Assertions.assertTrue(oracle.isRunning());
    }

    @Test
    void testTableCreation() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("BEGIN EXECUTE IMMEDIATE 'DROP TABLE test_table PURGE'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -942 THEN RAISE; END IF; END;");
            statement.execute("CREATE TABLE test_table (id NUMBER PRIMARY KEY, name VARCHAR2(50))");
            ResultSet resultSet = statement.executeQuery("SELECT table_name FROM user_tables WHERE table_name = 'TEST_TABLE'");
            assertTrue(resultSet.next());
        }
    }

    @Test
    void testDataInsertion() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("BEGIN EXECUTE IMMEDIATE 'DROP TABLE test_table PURGE'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -942 THEN RAISE; END IF; END;");
            statement.execute("CREATE TABLE test_table (id NUMBER PRIMARY KEY, name VARCHAR2(50))");
            statement.execute("INSERT INTO test_table (id, name) VALUES (1, 'testName')");
            ResultSet resultSet = statement.executeQuery("SELECT name FROM test_table WHERE id = 1");
            assertTrue(resultSet.next());
            assertEquals("testName", resultSet.getString("name"));
        }
    }

}
