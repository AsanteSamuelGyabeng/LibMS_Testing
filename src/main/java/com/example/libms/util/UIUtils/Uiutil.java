package com.example.libms.util.UIUtils;

import javafx.scene.control.Alert;

public class Uiutil {

    /**
     * Show an alert dialog with the given title and message.
     *
     * @param title   The title of the alert.
     * @param message The message of the alert.
     */
    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
