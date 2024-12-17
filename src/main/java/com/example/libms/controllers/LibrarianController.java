package com.example.libms.controllers;

import com.example.libms.model.Librarian;
import com.example.libms.util.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LibrarianController {

    private static Connection conn;

    public LibrarianController(Connection conn) {
        this.conn = conn; // Set the connection from the test setup
    }

    public static boolean createLibrarian(Librarian librarian) throws SQLException {
        String sql = "INSERT INTO librarians (name, email, password, employeeId, role) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, librarian.getName());
            ps.setString(2, librarian.getEmail());
            ps.setString(3, librarian.getPassword());
            ps.setString(4, librarian.getEmployeeId());
            ps.setString(5, librarian.getRole());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0; // Return true if rows were affected
        } catch (SQLException e) {
            System.err.println("Error inserting librarian: " + e.getMessage());
            throw e;
        }
    }
}
