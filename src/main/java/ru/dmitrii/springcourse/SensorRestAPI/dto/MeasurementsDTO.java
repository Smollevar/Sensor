package ru.dmitrii.springcourse.SensorRestAPI.dto;


import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timestamp;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.dmitrii.springcourse.SensorRestAPI.models.Sensor;
import ru.dmitrii.springcourse.SensorRestAPI.util.*;

import java.util.Date;
import java.util.List;

public class MeasurementsDTO {

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
    @Column(name = "datemeasurement")
    private Date dateMeasurement;

    public MeasurementsDTO(float value, boolean raining, Sensor sensor, Date dateMeasurement) {
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

    public static MeasurementsNotCreatedException collectErrorMessage(BindingResult br) {
        StringBuilder sb = new StringBuilder();
        List<FieldError> fieldErrors = br.getFieldErrors();
        for(FieldError fieldError : fieldErrors) {
            sb.append(fieldError.getField())
                    .append(" - ").append(fieldError.getDefaultMessage())
                    .append("; ");
        }
        return new MeasurementsNotCreatedException(sb.toString());
    }

    public static ResponseEntity<MeasurementErrorResponse> handleExceptionNotCreatedDTO(MeasurementParentException e){
        ResponseEntity<MeasurementErrorResponse> re = null;

        if (e instanceof MeasurementsNotCreatedException) {
            MeasurementErrorResponse response = new MeasurementErrorResponse(
                    e.getMessage(),
                    System.currentTimeMillis()
            );
            re = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } else if (e instanceof SensorNotExist) {
            MeasurementErrorResponse response = new MeasurementErrorResponse(
                    e.getMessage(),
                    System.currentTimeMillis()
            );
            re = new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        return re;
    }

    @Override
    public String toString() {
        return "MeasurementsDTO{" +
                "value=" + value +
                ", raining=" + raining +
                ", sensor=" + sensor +
                ", dateMeasurement=" + dateMeasurement +
                '}';
    }
}
