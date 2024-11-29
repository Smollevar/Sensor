package ru.dmitrii.springcourse.SensorRestAPI.util;

public class MeasurementsNotCreatedException extends MeasurementParentException {
    public MeasurementsNotCreatedException(String message) {
        super(message);
    }
}
