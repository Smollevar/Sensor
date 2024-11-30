package ru.dmitrii.springcourse.SensorRestAPI.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.dmitrii.springcourse.SensorRestAPI.dto.MeasurementsDTO;
import ru.dmitrii.springcourse.SensorRestAPI.dto.SensorDTO;
import ru.dmitrii.springcourse.SensorRestAPI.models.Measurements;
import ru.dmitrii.springcourse.SensorRestAPI.models.Sensor;
import ru.dmitrii.springcourse.SensorRestAPI.services.MeasurementsService;
import ru.dmitrii.springcourse.SensorRestAPI.services.SensorService;
import ru.dmitrii.springcourse.SensorRestAPI.util.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        if ((sensorService.findByName(measurementDTO.getSensorDTO().getName()) == null))
            throw new SensorNotExist("Sensor with such name not exist");

        measurementDTO.setDateMeasurement(new Date());

        Sensor sensor = sensorService.findByName(measurementDTO.getSensorDTO().getName());
        Measurements measurements = modelMapper.map(measurementDTO, Measurements.class);
        measurements.setSensor(sensor);
        sensor.getMeasurements().add(measurements);

        sensorService.save(sensor);
        measurementsService.save(measurements);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping()
    public List<MeasurementsDTO> getAllMeasurements() {

        List<Measurements> measurements = measurementsService.findAll();
        List<MeasurementsDTO> measRet = new ArrayList<>();

        for(int i = 0; i < measurements.size(); i++) {
            measRet.add(modelMapper.map(measurements.get(i), MeasurementsDTO.class));
            SensorDTO sdto = new SensorDTO();
            sdto.setName(measurements.get(i).getSensor().getName());
            measRet.get(i).setSensorDTO(sdto);
        }

        return measRet;
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
