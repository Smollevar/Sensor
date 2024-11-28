package ru.dmitrii.springcourse.SensorRestAPI.util;

public class SensorAlreadyExist extends SensorParentException {
    public SensorAlreadyExist(String message) {
        super(message);
    }
}
