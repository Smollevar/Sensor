package ru.dmitrii.springcourse.SensorRestAPI.util;

public class SensorNotCreatedException extends SensorParentException {
    public SensorNotCreatedException(String message) {
        super(message);
    }
}
