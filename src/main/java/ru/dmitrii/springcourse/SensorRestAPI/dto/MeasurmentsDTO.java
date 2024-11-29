package ru.dmitrii.springcourse.SensorRestAPI.dto;


import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timestamp;
import org.hibernate.validator.constraints.Range;
import ru.dmitrii.springcourse.SensorRestAPI.models.Sensor;

import java.util.Date;

public class MeasurmentsDTO {

    @Column(name = "value")
    @Range(min = -100, max = 100, message = "temperature must being between -100 and 100")
    private float value;

    @Column(name = "raining")
    @NotNull(message = "filed must being filled up")
    private boolean raining;

    @OneToOne
    @JoinColumn(name = "name", referencedColumnName = "name")
    @NotNull(message = "Each measure must have sensor")
    // todo add validation of the sensor from database.
    private Sensor sensor;

    @Timestamp
    private Date dateMeasurement;

    public MeasurmentsDTO(float value, boolean raining, Sensor sensor, Date dateMeasurement) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
        this.dateMeasurement = dateMeasurement;
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

//    public static MeasurementsNotCreatedException collectErrorMessage(bindingResult)
}
