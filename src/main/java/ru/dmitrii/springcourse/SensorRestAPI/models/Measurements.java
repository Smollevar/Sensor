package ru.dmitrii.springcourse.SensorRestAPI.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timestamp;
import org.hibernate.validator.constraints.Range;

import java.util.Date;

@Entity
@Table(name = "Measurements")
public class Measurements {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "value")
    @NotNull(message = "field value cannot being null")
    @Range(min = -100, max = 100, message = "temperature must being between -100 and 100")
    private float value;

    @Column(name = "raining")
    @NotNull(message = "filed must being filled up")
    private boolean raining;

    @OneToOne
    @JoinColumn(name = "name", referencedColumnName = "name")
    @NotNull(message = "Each measure must have sensor")
    private Sensor sensor;

    @Timestamp
    private Date dateMeasurement;


    public Measurements() {
    }

    public Measurements(int id, float value, boolean raining, Sensor sensor, Date dateMeasurement) {
        this.id = id;
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
        this.dateMeasurement = dateMeasurement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Range(min = -100, max = 100, message = "temperature must being between -100 and 100")
    public float getValue() {
        return value;
    }

    public void setValue(@Range(min = -100, max = 100, message = "temperature must being between -100 and 100") float value) {
        this.value = value;
    }

    @NotNull(message = "filed must being filled up")
    public boolean isRaining() {
        return raining;
    }

    public void setRaining(@NotNull(message = "filed must being filled up") boolean raining) {
        this.raining = raining;
    }

    public @NotNull(message = "Each measure must have sensor") Sensor getSensor() {
        return sensor;
    }

    public void setSensor(@NotNull(message = "Each measure must have sensor") Sensor sensor) {
        this.sensor = sensor;
    }

    public Date getDateMeasurement() {
        return dateMeasurement;
    }

    public void setDateMeasurement(Date dateMeasurement) {
        this.dateMeasurement = dateMeasurement;
    }
}
