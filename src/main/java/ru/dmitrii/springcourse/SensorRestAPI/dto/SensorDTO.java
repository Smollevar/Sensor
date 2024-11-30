package ru.dmitrii.springcourse.SensorRestAPI.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.dmitrii.springcourse.SensorRestAPI.util.SensorAlreadyExist;
import ru.dmitrii.springcourse.SensorRestAPI.util.SensorErrorResponse;
import ru.dmitrii.springcourse.SensorRestAPI.util.SensorNotCreatedException;
import ru.dmitrii.springcourse.SensorRestAPI.util.SensorParentException;

import java.util.List;
import java.util.Objects;

public class SensorDTO {

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 3, max = 30, message = "Name should be between 3 and 30 symbols")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(@Size(min = 2, max = 30, message = "Name should be between 2 and 30 symbols") String name) {
        this.name = name;
    }

    public static SensorNotCreatedException collectErrorMessage(BindingResult bindingResult) {
        StringBuilder errors = new StringBuilder();

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            errors.append(fieldError.getField())
                    .append(" - ").append(fieldError.getDefaultMessage())
                    .append("; ");
        }
        return new SensorNotCreatedException(errors.toString());
    }

    public static ResponseEntity<SensorErrorResponse> handleExceptionNotCreatedDTO(SensorParentException e){
        ResponseEntity<SensorErrorResponse> re = null;

        if (e instanceof SensorNotCreatedException) {
            SensorErrorResponse response = new SensorErrorResponse(
                    e.getMessage(),
                    System.currentTimeMillis()
            );
            re = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } else if (e instanceof SensorAlreadyExist) {
            SensorErrorResponse response = new SensorErrorResponse(
                    "Theres already exist sensor with such name",
                    System.currentTimeMillis()
            );
            re = new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        return re;
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
