package com.example.client.util;

import com.example.client.Main;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * The WindowUtil class provides utility methods for initializing and displaying
 * the main application stage and showing warning alerts.
 */
public class WindowUtil {

    /**
     * Initializes and shows the main application stage with the specified layout.
     *
     * @param stage the primary stage for the application
     * @throws IOException if the main-view.fxml file cannot be loaded
     */
    public static void initAndShowMainStage(Stage stage) throws IOException {
        FXMLLoader mainFxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene mainScene = new Scene(mainFxmlLoader.load(), 440, 400);
        stage.setTitle("Temperature regulator");
        stage.setScene(mainScene);
        stage.show();
    }

    /**
     * Displays a warning alert with the specified temperature value after a delay of 3 seconds.
     *
     * @param value the current temperature value that triggered the warning
     */
    public static void initAndShowWarningWindow(String value) throws InterruptedException {
        Platform.runLater(() -> {
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Temperature is out of the allowed range.");
                alert.setContentText("Current value is " + value + ". The temperature must be in the range of -200 and 1000 degrees.");
                alert.show();
            });
            delay.play();
        });

    }
}