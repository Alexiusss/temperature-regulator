package com.example.client.util;

import com.example.client.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowUtil {
    public static void initAndShowMainStage(Stage stage) throws IOException {
        FXMLLoader mainFxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene mainScene = new Scene(mainFxmlLoader.load(), 440, 400);
        stage.setTitle("Temperature regulator");
        stage.setScene(mainScene);
        stage.show();
    }

    public static void initAndShowWarningWindow(String value) throws InterruptedException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Temperature is out of the allowed range.");
        alert.setContentText("Current value is " + value + ". The temperature must be in the range of -200 and 1000 degrees.");
        Thread.sleep(3000);
        alert.showAndWait();
    }
}