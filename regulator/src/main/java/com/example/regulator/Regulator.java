package com.example.regulator;

import java.util.List;

public interface Regulator {
    void setTemperature(Float value);
    List<Float> getTemperatureList();
    void clearTemperatureList();
}