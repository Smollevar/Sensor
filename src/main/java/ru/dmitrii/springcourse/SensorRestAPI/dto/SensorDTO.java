package ru.dmitrii.springcourse.SensorRestAPI.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class SensorDTO {

    @Column(name = "name")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 symbols")
    private String name;

    public String getName() {
        return name;
    }

    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 symbols")
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorDTO sensorDTO = (SensorDTO) o;
        return Objects.equals(name, sensorDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "SensorDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
