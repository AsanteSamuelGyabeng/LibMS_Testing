package com.example.libms.controllers;

import com.example.libms.services.StaffService;
import com.example.libms.util.UIUtils.Uiutil;
import com.example.libms.util.DB;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;

public class StaffController {

    @FXML
    public TextField nameInput;

    @FXML
    public TextField emailInput;

    @FXML
    public PasswordField passwordInput;

    private final StaffService staffService;

    // Constructor initializes the service with the database connection.
    public StaffController() throws SQLException {
        Connection connection = DB.getConnection();
        this.staffService = new StaffService(connection);
    }

    /**
     * Handle sign-up action for registering a new staff member.
     */
    @FXML
    public void handleSignUpAction(String text, String emailInputText, String passwordInputText) {
        String username = text;
        String email = emailInputText;
        String password = passwordInputText;

        if (areFieldsValid(username, email, password)) {
            try {
                boolean isRegistered = staffService.registerStaff(username, email, password);

                if (isRegistered) {
                    Uiutil.showAlert("Success", "Staff member registered successfully!");
                    clearFields();
                } else {
                    Uiutil.showAlert("Error", "Registration failed. Please try again.");
                }
            } catch (SQLException e) {
                Uiutil.showAlert("Database Error", "An error occurred: " + e.getMessage());
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Validate if the input fields are not empty.
     *
     * @param name     The name to check.
     * @param email    The email to check.
     * @param password The password to check.
     * @return true if all fields are filled, false otherwise.
     */
    boolean areFieldsValid(String name, String email, String password) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Uiutil.showAlert("Error", "All fields are required.");
            return false;
        }
        return true;
    }

    /**
     * Clear the input fields.
     */
    private void clearFields() {
        nameInput.clear();
        emailInput.clear();
        passwordInput.clear();
    }
}
