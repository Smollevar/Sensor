package ru.dmitrii.springcourse.SensorRestAPI.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.dmitrii.springcourse.SensorRestAPI.dto.MeasurementsDTO;
import ru.dmitrii.springcourse.SensorRestAPI.models.Measurements;
import ru.dmitrii.springcourse.SensorRestAPI.services.MeasurementsService;
import ru.dmitrii.springcourse.SensorRestAPI.services.SensorService;
import ru.dmitrii.springcourse.SensorRestAPI.util.*;

import java.util.Date;

@RestController
public class MeasurementsController {
    private final MeasurementsService measurementsService;
    private final ModelMapper modelMapper;
    private final SensorService sensorService;

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, ModelMapper modelMapper, SensorService sensorService) {
        this.measurementsService = measurementsService;
        this.modelMapper = modelMapper;
        this.sensorService = sensorService;
    }

    @PostMapping("/measurement/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementsDTO measurementDTO,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) throw MeasurementsDTO.collectErrorMessage(bindingResult);

        if ((sensorService.findByName(measurementDTO.getSensor().getName()) == null) || (measurementDTO.getSensor().getName() == null))
            throw new SensorNotExist("Sensor with such name not exist");
        measurementDTO.setDateMeasurement(new Date());
        String sensorName = measurementDTO.getSensor().getName();
        Measurements measurements = modelMapper.map(measurementDTO, Measurements.class);
        measurements.setSensor(sensorService.findByName(sensorName));
        measurementsService.save(measurements);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @ExceptionHandler()
    private ResponseEntity<MeasurementErrorResponse> handleExceptionNotCreated(MeasurementParentException e) {
        return MeasurementsDTO.handleExceptionNotCreatedDTO(e);
    }

    public Measurements convertToMeasure(MeasurementsDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurements.class);
    }
}
