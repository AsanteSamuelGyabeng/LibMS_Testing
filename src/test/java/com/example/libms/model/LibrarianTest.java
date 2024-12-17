package com.example.libms.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LibrarianTest {

    private Librarian librarian;

    @BeforeEach
    void setUp() {
        // Initialize a Librarian object before each test
        librarian = new Librarian(1, "John Doe", "john.doe@example.com", "password123", "EMP12345", "Admin");
    }

    @Test
    void testConstructor() {
        // Verify the constructor initializes correctly
        assertEquals(1, librarian.getId());
        assertEquals("John Doe", librarian.getName());
        assertEquals("john.doe@example.com", librarian.getEmail());
        assertEquals("password123", librarian.getPassword());
        assertEquals("EMP12345", librarian.getEmployeeId());
        assertEquals("Librarian", librarian.getRole());
    }

    @Test
    void testGetEmployeeId() {
        // Test the getEmployeeId method
        assertEquals("EMP12345", librarian.getEmployeeId());
    }

    @Test
    void testDisplayUserRole() {
        java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outputStream));

        librarian.displayUserRole();

        System.setOut(System.out);

        String expectedOutput = "John Doe is a Librarian with employee ID: EMP12345";
        assertEquals(expectedOutput, outputStream.toString().trim());
    }
}
