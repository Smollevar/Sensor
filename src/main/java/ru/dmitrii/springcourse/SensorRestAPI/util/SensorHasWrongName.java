package ru.dmitrii.springcourse.SensorRestAPI.util;

public class SensorHasWrongName extends RuntimeException {
    public SensorHasWrongName(String message) {
        super(message);
    }
}
