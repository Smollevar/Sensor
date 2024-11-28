package ru.dmitrii.springcourse.SensorRestAPI.util;

import ru.dmitrii.springcourse.SensorRestAPI.dto.SensorDTO;

import java.util.Scanner;

public class CreateNewSensor {
    public static SensorDTO createNewSensor() {
        SensorDTO sensorDTO = new SensorDTO();
        Scanner scanner = new Scanner(System.in);
        sensorDTO.setName(scanner.next());
        System.out.println(sensorDTO.getName());
        return sensorDTO;
    }
}
