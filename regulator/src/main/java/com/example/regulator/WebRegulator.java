package com.example.regulator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The WebRegulator class implements the Regulator interface and provides methods
 * for managing a list of temperature values. This class follows the Singleton
 * design pattern to ensure only one instance is created.
 */
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

    /**
     * Sets the temperature by generating a list of interpolated values between
     * the last value in the temperature list and the provided value. The number of
     * interpolated values is random(6) + 3.
     *
     * @param value the target temperature value
     * @return a list of interpolated temperature values
     */
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

    /**
     * Generates a list of interpolated values between the start and end values.
     * The count of interpolated values is specified by the count parameter.
     *
     * @param start start of the interval
     * @param end end of the interval
     * @param count count of output interpolated numbers
     * @throws IllegalArgumentException if count is less than 3
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