package com.example.client;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.client.util.WindowUtil.initAndShowMainStage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        initAndShowMainStage(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}