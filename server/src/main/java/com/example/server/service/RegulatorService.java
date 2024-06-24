package com.example.server.service;

import com.example.regulator.Regulator;
import com.example.regulator.WebRegulator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegulatorService {
    Regulator regulator;

    public RegulatorService() {
        this.regulator = WebRegulator.getInstance();
    }

    public void setTemperature(String number){
        regulator.setTemperature(Float.valueOf(number));
    }

    public Float getCurrentTemperature() {
        List<Float> temperatureList = getAll();
        if (temperatureList == null || temperatureList.isEmpty()) {
            throw new IllegalStateException("Temperature list is empty or null.");
        }
        return temperatureList.get(temperatureList.size() - 1);
    }

    public Float getTemperatureWithShift(int shift, int count) {
        List<Float> all = getAll();

        if (all == null || all.isEmpty()) {
            throw new IllegalStateException("Temperature list is empty or null.");
        }

        int size = all.size();

        if (count <= 0 || count > size) {
            throw new IllegalArgumentException("Invalid count value.");
        }

        if (shift <= 0 || shift > count) {
            throw new IllegalArgumentException("Invalid shift value.");
        }

        List<Float> sublist = all.subList(size - count, size);

        return sublist.get(sublist.size() - shift - 1);
    }

    public List<Float> getAll() {
        return regulator.getTemperatureList();
    }

    public void clearTemperatureList() {
        regulator.clearTemperatureList();
    }
}
