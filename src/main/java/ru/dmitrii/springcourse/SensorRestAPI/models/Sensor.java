package ru.dmitrii.springcourse.SensorRestAPI.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "sensor")
public class Sensor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 symbols")
    private String name;

    @OneToOne(mappedBy = "sensor")
    private Measurements measurement;

    public Sensor() {
    }

    public Sensor(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Measurements getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurements measurement) {
        this.measurement = measurement;
    }
}
