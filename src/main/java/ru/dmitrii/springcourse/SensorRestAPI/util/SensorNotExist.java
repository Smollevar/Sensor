package ru.dmitrii.springcourse.SensorRestAPI.util;

public class SensorNotExist extends MeasurementParentException {
  public SensorNotExist(String message) {
    super(message);
  }
}
