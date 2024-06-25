package com.example.client.controller;

import com.example.client.service.WebService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.io.IOException;
import java.util.List;

import static com.example.client.util.WindowUtil.initAndShowWarningWindow;

public class TemperatureController {

    private final WebService webService = new WebService();
    private final ObservableList<Float> itemList = FXCollections.observableArrayList();
    private final SpinnerValueFactory<Double> spinner = new SpinnerValueFactory.DoubleSpinnerValueFactory(-5000, 5000, 0, 0.1);

    @FXML
    private ListView<Float> listView;

    @FXML
    private Spinner<Double> doubleSpinner;

    @FXML
    private void initialize() {
        listView.setItems(itemList);
        doubleSpinner.setValueFactory(spinner);
    }
    @FXML
    protected void onGetButtonClick() throws IOException {
        List<Float> data = webService.getData();
        listView.getItems().addAll(data);
    }

    @FXML
    protected void onPostButtonClick() throws IOException, InterruptedException {
        Float currenTemperature = webService.setTemperature(doubleSpinner.getValue());
        if (currenTemperature < -200 || currenTemperature > 1000) {
            initAndShowWarningWindow(currenTemperature.toString());
        }
    }
}