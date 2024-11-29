package ru.dmitrii.springcourse.SensorRestAPI.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.dmitrii.springcourse.SensorRestAPI.dto.MeasurmentsDTO;
import ru.dmitrii.springcourse.SensorRestAPI.services.MeasurementsService;

@RestController
public class MeasurementsController {
    private final MeasurementsService measurementsService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, ModelMapper modelMapper) {
        this.measurementsService = measurementsService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/measurement/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurmentsDTO measurmentDTO,
                                                     BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) throw MeasurementsDTO.collectErrorMessage(bindingResult);
        return null;
    }
}
