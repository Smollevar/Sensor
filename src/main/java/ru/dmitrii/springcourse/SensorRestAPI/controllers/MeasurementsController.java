package ru.dmitrii.springcourse.SensorRestAPI.controllers;

import jakarta.validation.Valid;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.dmitrii.springcourse.SensorRestAPI.dto.MeasurementsDTO;
import ru.dmitrii.springcourse.SensorRestAPI.models.Measurements;
import ru.dmitrii.springcourse.SensorRestAPI.models.Sensor;
import ru.dmitrii.springcourse.SensorRestAPI.services.MeasurementsService;
import ru.dmitrii.springcourse.SensorRestAPI.services.SensorService;
import ru.dmitrii.springcourse.SensorRestAPI.util.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
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

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementsDTO measurementDTO,
                                                     BindingResult bindingResult) {

        if (bindingResult.hasErrors()) throw MeasurementsDTO.collectErrorMessage(bindingResult);

        if ((sensorService.findByName(measurementDTO.getSensor().getName()) == null))
            throw new SensorNotExist("Sensor with such name not exist");

        measurementDTO.setDateMeasurement(new Date());

        Sensor sensor = sensorService.findByName(measurementDTO.getSensor().getName());
        Measurements measurements = modelMapper.map(measurementDTO, Measurements.class);
        sensor.getMeasurements().add(measurements);
        measurements.setSensor(sensorService.findByName(measurementDTO.getSensor().getName()));

        sensorService.save(sensor);
        measurementsService.save(measurements);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping()
    public List<MeasurementsDTO> getAllMeasurements() {
        List<Measurements> measurements = measurementsService.findAll();
        List<MeasurementsDTO> measRet = new ArrayList<>();
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.addMappings(new PropertyMap<Measurements, MeasurementsDTO>() {
//            @Override
//            protected void configure() {
//                skip(measurements.get(0).getSensor().getMeasurements());
//            }
//        });
        for(Measurements measurement : measurements) { // todo return only name of sensor without his own field of measures that make measure.
            measRet.add(modelMapper.map(measurement, MeasurementsDTO.class));
        }
        return measRet;
        //        return measurementsService.findAll().stream().map(this::convertToMeasureDTO).collect(Collectors.toList());
    }

    @ExceptionHandler()
    private ResponseEntity<MeasurementErrorResponse> handleExceptionNotCreated(MeasurementParentException e) {
        return MeasurementsDTO.handleExceptionNotCreatedDTO(e);
    }

    public Measurements convertToMeasure(MeasurementsDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurements.class);
    }

    public MeasurementsDTO convertToMeasureDTO(Measurements measurements) {
        return modelMapper.map(measurements, MeasurementsDTO.class);
    }
}
