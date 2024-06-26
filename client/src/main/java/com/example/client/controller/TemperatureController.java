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

/**
 * The TemperatureController class handles the user interactions for the temperature regulator UI,
 * including fetching and setting temperature values.
 */
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

    /**
     * Handles the action of the "Get data" button click by fetching temperature data from the web service
     * and updating the ListView with the retrieved data.
     *
     * @throws IOException if an I/O error occurs during the web service call
     */
    @FXML
    protected void onGetButtonClick() throws IOException {
        List<Float> data = webService.getData();
        listView.getItems().addAll(data);
    }

    /**
     * Handles the action of the "Set" button click by setting the temperature via the web service
     * and displaying a warning if the current temperature is out of the allowed range.
     *
     * @throws IOException if an I/O error occurs during the web service call
     * @throws InterruptedException if the warning window display is interrupted
     */
    @FXML
    protected void onPostButtonClick() throws IOException, InterruptedException {
        Float currenTemperature = webService.setTemperature(doubleSpinner.getValue());
        if (currenTemperature < -200 || currenTemperature > 1000) {
            initAndShowWarningWindow(currenTemperature.toString());
        }
    }
}