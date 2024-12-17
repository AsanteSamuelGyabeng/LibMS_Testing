package com.example.libms.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StaffService {

    private final Connection connection;

    public StaffService(Connection connection) {
        this.connection = connection;
    }

    /**
     * Register a new staff member in the database.
     *
     * @param username The staff member's username.
     * @param email    The staff member's email.
     * @param password The staff member's password.
     * @return true if the staff member is successfully registered, false otherwise.
     * @throws SQLException if a database error occurs.
     */
    public boolean registerStaff(String username, String email, String password) throws SQLException {
        String sql = "INSERT INTO staff (username, email, password) VALUES (?, ?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);

            return preparedStatement.executeUpdate() > 0;
        }
    }
}
