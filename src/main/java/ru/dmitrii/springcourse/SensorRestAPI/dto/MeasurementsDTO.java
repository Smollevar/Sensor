package ru.dmitrii.springcourse.SensorRestAPI.dto;

import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timestamp;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.dmitrii.springcourse.SensorRestAPI.util.*;

import java.util.Date;
import java.util.List;

public class MeasurementsDTO {

    @Range(min = -100, max = 100, message = "temperature must being between -100 and 100")
    private float value;

    @NotNull(message = "filed must being filled up")
    private boolean raining;

    @Timestamp
    private Date dateMeasurement;

    @NotNull(message = "Each measure must have sensor")
    private SensorDTO sensorDTO;

    public MeasurementsDTO() {
    }

    public MeasurementsDTO(float value, boolean raining, Date dateMeasurement) {
        this.value = value;
        this.raining = raining;
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

    public @NotNull(message = "Each measure must have sensor") SensorDTO getSensorDTO() {
        return sensorDTO;
    }

    public void setSensorDTO(@NotNull(message = "Each measure must have sensor") SensorDTO sensorDTO) {
        this.sensorDTO = sensorDTO;
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
                ", dateMeasurement=" + dateMeasurement +
                '}';
    }
}
