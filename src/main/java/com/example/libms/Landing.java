package com.example.libms;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Landing extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage splashStage = new Stage();
        splashStage.initStyle(StageStyle.UNDECORATED);

        FXMLLoader splashLoader = new FXMLLoader(Landing.class.getResource("hello-view.fxml"));
        Scene splashScene = new Scene(splashLoader.load(), 600 , 400);
        splashStage.setScene(splashScene);
        splashStage.show();

        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> {
            try {
                splashStage.close();
                FXMLLoader mainLoader = new FXMLLoader(Landing.class.getResource("login.fxml"));
                Scene mainScene = new Scene(mainLoader.load(), 620, 500);
                primaryStage.setScene(mainScene);
                primaryStage.setTitle("Login");
                primaryStage.show();
            } catch (Exception e) {
                showAlert("Error", "An error occurred while loading the main stage: " + e.getMessage());
            }
        });
        pause.play();
    }
    void showAlert (String message, String contentText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.setContentText(contentText);
    }

    public static void main(String[] args) {
        launch();
    }
}
