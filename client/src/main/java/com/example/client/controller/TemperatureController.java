package com.example.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import static com.example.client.util.WindowUtil.initAndShowWarningWindow;

public class TemperatureController {

    @FXML
    private Label warnText;

    @FXML
    protected void onWarningButtonClick() throws InterruptedException {
        initAndShowWarningWindow("-505.15");
    }
}