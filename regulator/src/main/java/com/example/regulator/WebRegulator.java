package com.example.regulator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class WebRegulator implements Regulator {

    private static volatile WebRegulator instance;

    private final List<Float> temperatureList;

    private WebRegulator() {
        this.temperatureList = new ArrayList<>();
    }

    public static WebRegulator getInstance() {
        if (instance == null) {
            synchronized (WebRegulator.class) {
                if (instance == null) {
                    instance = new WebRegulator();
                }
            }
        }
        return instance;
    }

    @Override
    public List<Float> setTemperature(Float value) {
        int numbersCount = 3 + ThreadLocalRandom.current().nextInt(6);
        Float lastValue = 0f;
        if (!temperatureList.isEmpty()){
            lastValue = temperatureList.get(temperatureList.size() - 1);
        }
        List<Float> values = getInterpolatedValues(lastValue, value, numbersCount);
        temperatureList.addAll(values);
        return values;
    }

    /***
     * Interpolating method
     * @param start start of the interval
     * @param end end of the interval
     * @param count count of output interpolated numbers
     * @return list of interpolated number with specified count
     */
    private List<Float> getInterpolatedValues(float start, float end, int count) {
        if (count < 3) {
            throw new IllegalArgumentException("Interpolate: illegal count!");
        }
        List<Float> list = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            float value = start + i * (end - start) / (count - 1);
            value = (float) (Math.floor(value * 100) / 100);
            list.add(i, value);
        }
        return list;
    }

    @Override
    public List<Float> getTemperatureList() {
        return temperatureList;
    }

    @Override
    public void clearTemperatureList() {
        temperatureList.clear();
    }

    public static void destroyInstance() {
        instance = null;
    }
}