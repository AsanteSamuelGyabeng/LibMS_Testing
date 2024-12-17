package com.example.libms.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserModelTest {

    static class TestUserModel extends UserModel {
        public TestUserModel(int id, String name, String email, String password, String role) {
            super(id, name, email, password, role);
        }

        @Override
        public void displayUserRole() {
            System.out.println("User role: " + getRole());
        }
    }

    @Test
    void testUserModelCreation() {
        UserModel user = new TestUserModel(1, "John Doe", "john.doe@example.com", "password123", "admin");

        assertEquals(1, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals("admin", user.getRole());
    }

    @Test
    void testDisplayUserRole() {
        UserModel user = new TestUserModel(2, "Jane Smith", "jane.smith@example.com", "securePass", "user");

        assertDoesNotThrow(user::displayUserRole);
    }
}