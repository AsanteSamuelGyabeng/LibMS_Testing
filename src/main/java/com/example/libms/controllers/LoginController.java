package com.example.libms.controllers;

import com.example.libms.services.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    TextField usernameField;

    @FXML
    PasswordField passwordField;

    LoginService loginService = new LoginService();

    @FXML
    void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (loginService.authenticateUser(username, password)) {
            navigateToDashboard(event, username);
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Incorrect username or password.");
        }
    }

    void navigateToDashboard(ActionEvent event, String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/libms/dashboard.fxml"));
            Scene scene = new Scene(loader.load());

            LibraryDashboardController dashboardController = loader.getController();
            dashboardController.setUsername(username);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Library Dashboard");
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load the dashboard: " + e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the dashboard.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

     void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
