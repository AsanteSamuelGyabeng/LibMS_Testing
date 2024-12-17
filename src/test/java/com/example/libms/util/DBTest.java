package com.example.libms.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DBTest {

    private Connection mockConnection;

    @BeforeEach
    public void setUp() {
        // Initialize the mock connection before each test
        mockConnection = mock(Connection.class);
    }

    @Test
    public void testGetConnection_withMockedConnection() throws SQLException {
        // Set the mocked connection
        DB.setConnection(mockConnection);

        // Verify that DB.getConnection() returns the mocked connection
        Connection connection = DB.getConnection();
        assertEquals(mockConnection, connection, "The connection should be the mocked connection");
    }

    @Test
    public void testGetConnection_withoutMockedConnection() throws SQLException {
        // Ensure that the mocked connection is not set
        DB.setConnection(null);

        // Call getConnection and check if the connection is not null
        Connection connection = DB.getConnection();
        assertNotNull(connection, "The connection should not be null");

        // In real tests, you might want to check if it's a valid connection object, 
        // but testing a real database connection is generally not done in unit tests.
        // Instead, this test could be used to check the flow of connection creation.
    }

    @Test
    public void testSetConnection() throws SQLException {
        // Set the mocked connection
        DB.setConnection(mockConnection);

        // Verify the set connection is returned correctly by DB.getConnection()
        assertSame(mockConnection, DB.getConnection(), "The set connection should be returned");
    }
}
