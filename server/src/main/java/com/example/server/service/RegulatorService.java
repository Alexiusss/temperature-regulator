package com.example.server.service;

import com.example.regulator.Regulator;
import com.example.regulator.WebRegulator;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The RegulatorService class provides methods to interact with the temperature
 * regulator, including setting temperatures, retrieving current and shifted values,
 * and clearing the temperature list.
 */
@Service
public class RegulatorService {
    Regulator regulator;

    public RegulatorService() {
        this.regulator = WebRegulator.getInstance();
    }

    public List<Float> setTemperature(String number) {
        return regulator.setTemperature(Float.valueOf(number));
    }

    /**
     * Retrieves the current (most recent) temperature value.
     *
     * @return the most recent temperature value
     * @throws IllegalStateException if the temperature list is empty or null
     */
    public Float getCurrentTemperature() {
        List<Float> temperatureList = getAll();
        if (temperatureList == null || temperatureList.isEmpty()) {
            throw new IllegalStateException("Temperature list is empty or null.");
        }
        return temperatureList.get(temperatureList.size() - 1);
    }

    /**
     * Retrieves a sublist of temperature values based on the specified shift and count.
     *
     * @param shift the number of values to shift from the end of the list
     * @param count the number of values to retrieve
     * @return a sublist of temperature values
     * @throws IllegalStateException if the temperature list is null
     * @throws IllegalArgumentException if the count or shift values are invalid
     */
    public List<Float> getValueWithShift(int shift, int count) {
        List<Float> all = getAll();

        if (all == null) {
            throw new IllegalStateException("Temperature list is null.");
        }

        if ((shift == 0 && count == 0) || all.isEmpty()) {
            return all;
        }

        int size = all.size();

        if (count <= 0 || count > size) {
            throw new IllegalArgumentException("Invalid count value.");
        }

        if (shift <= 0 || shift < count) {
            throw new IllegalArgumentException("Invalid shift value.");
        }

        int start = size - shift;
        int end = start + count;
        return all.subList(start, end);
    }

    private List<Float> getAll() {
        return regulator.getTemperatureList();
    }

    public void clearTemperatureList() {
        regulator.clearTemperatureList();
    }
}
