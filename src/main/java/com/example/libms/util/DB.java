package com.example.libms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

    private static Connection testConnection; // Mocked connection for testing

    /**
     * Set a mocked connection for testing purposes.
     * @param connection the mocked connection
     */
    public static void setConnection(Connection connection) {
        testConnection = connection;
    }

    /**
     * Get a database connection.
     * @return the database connection (mocked or real)
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        if (testConnection != null) {
            // Return the mocked connection if it is set
            return testConnection;
        }

        // Real database connection for production
        String url = "jdbc:mysql://localhost:3306/librarymanagementsystem";
        String username = "root";
        String password = "";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected successfully");
            return connection;
        } catch (SQLException e) {
            System.out.println("Error: Unable to connect to the database");
            e.printStackTrace();
            return null;
        }
    }
}
