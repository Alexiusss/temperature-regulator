package com.example.regulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The WebRegulator class implements the Regulator interface and provides methods
 * for managing a list of temperature values. This class follows the Singleton
 * design pattern to ensure only one instance is created.
 */
public class WebRegulator implements Regulator {

    private static volatile WebRegulator instance;

    private final List<Float> temperatureList;

    private final ReentrantLock lock;

    private WebRegulator() {
        this.temperatureList = Collections.synchronizedList(new ArrayList<>());
        lock = new ReentrantLock(true);
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
        lock.lock();
        try {
            int numbersCount = 3 + ThreadLocalRandom.current().nextInt(6);
            Float lastValue = 0f;
            if (!temperatureList.isEmpty()) {
                lastValue = temperatureList.get(temperatureList.size() - 1);
            }
            List<Float> values = getInterpolatedValues(lastValue, value, numbersCount);
            temperatureList.addAll(values);
            return values;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Generates a list of interpolated values between the start and end values.
     * The count of interpolated values is specified by the count parameter.
     *
     * @param start start of the interval
     * @param end   end of the interval
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
        lock.lock();
        try {
            return temperatureList;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void clearTemperatureList() {
        lock.lock();
        try {
            temperatureList.clear();
        } finally {
            lock.unlock();
        }
    }

    public static void destroyInstance() {
        instance = null;
    }

    /**
     * Adjusts the regulator based on the operation specified by the operation byte.
     *
     * @param operation the byte that specifies the operation and parameters
     * @param inData    the temperature value to be set
     * @param outData   the list to store the previous values
     * @param offSet    the shift relative to the last value index
     * @return 0 if successful, error codes 1-3 otherwise
     */
    @Override
    public int adjustTemperature(byte operation, float inData, List<Float> outData, int offSet) {
        boolean isClearingRequired = (operation & 0b00000001) != 0;
        boolean isTemperatureSettingRequired = (operation & 0b00000010) != 0;
        boolean isNeedToReceiveData = (operation & 0b00000100) != 0;
        int readCount = (operation >> 3) & 0b00001111;

        lock.lock();
        try {
            if (isClearingRequired) {
                clearTemperatureList();
            }
            if (isTemperatureSettingRequired) {
                setTemperature(inData);
            }
            if (isNeedToReceiveData) {
                int size = temperatureList.size();
                if (size == 0) {
                    return 2;
                }
                if (offSet > size - readCount) {
                    return 1;
                }
                outData.addAll(temperatureList.subList(size - readCount - offSet, size - offSet));
                if (inData < -200 || inData > 1000) {
                    return 3;
                }
            }
            return 0;
        } finally {
            lock.unlock();
        }
    }
}