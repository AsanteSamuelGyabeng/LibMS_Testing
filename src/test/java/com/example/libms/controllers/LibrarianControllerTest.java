package com.example.libms.controllers;

import com.example.libms.model.Librarian;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LibrarianControllerTest {

    @Mock
    private Connection conn;  // Mocked connection

    @Mock
    private PreparedStatement ps;  // Mocked PreparedStatement

    private LibrarianController librarianController;

    @BeforeEach
    public void setUp() throws SQLException {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Mock behavior for the connection and prepared statement
        when(conn.prepareStatement(anyString())).thenReturn(ps);

        // Create a new instance of the controller with the mocked connection
        librarianController = new LibrarianController(conn);
    }

    @Test
    public void testCreateLibrarian_Success() throws SQLException {
        // Arrange
        Librarian librarian = new Librarian(1, "John Doe", "johndoe@example.com", "password123", "EMP123", "Admin");

        // Simulate successful insert
        when(ps.executeUpdate()).thenReturn(1); // Simulate affected rows being > 0

        // Act
        boolean result = librarianController.createLibrarian(librarian);

        // Assert
        assertTrue(result);
        verify(ps).setString(1, "John Doe");  // Matching name
        verify(ps).setString(2, "johndoe@example.com");  // Matching email
        verify(ps).setString(3, "password123");  // Matching password
        verify(ps).setString(4, "EMP123");  // Matching employee ID
        verify(ps).setString(5, "Librarian");  // Matching role
        verify(ps).executeUpdate();
    }

    @Test
    public void testCreateLibrarian_Failure() throws SQLException {
        // Arrange
        Librarian librarian = new Librarian(1, "Jane Doe", "janedoe@example.com", "password456", "EMP124", "Librarian");

        // Simulate failed insert
        when(ps.executeUpdate()).thenReturn(0); // Simulate affected rows being 0

        // Act
        boolean result = librarianController.createLibrarian(librarian);

        // Assert
        assertFalse(result);
        verify(ps).setString(1, "Jane Doe");  // Matching name
        verify(ps).setString(2, "janedoe@example.com");  // Matching email
        verify(ps).setString(3, "password456");  // Matching password
        verify(ps).setString(4, "EMP124");  // Matching employee ID
        verify(ps).setString(5, "Librarian");  // Matching role
        verify(ps).executeUpdate();
    }

    @Test
    public void testCreateLibrarian_Exception() throws SQLException {
        // Arrange
        Librarian librarian = new Librarian(1, "Alice Smith", "alice@example.com", "password789", "EMP125", "Admin");

        // Simulate exception on executeUpdate
        when(ps.executeUpdate()).thenThrow(new SQLException("Database error"));

        // Act & Assert
        assertThrows(SQLException.class, () -> {
            librarianController.createLibrarian(librarian);
        });
    }
}
