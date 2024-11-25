package ru.dmitrii.springcourse.SensorRestAPI.util;

public class SensorAlreadyExist extends RuntimeException {
    public SensorAlreadyExist(String message) {
        super(message);
    }
}
