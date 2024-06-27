package com.example.regulator;

import java.util.List;

public interface Regulator {
    List<Float> setTemperature(Float value);
    List<Float> getTemperatureList();
    void clearTemperatureList();
    int adjustTemperature(byte operation, float inData, List<Float> outData, int offSet);
}