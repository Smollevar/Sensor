package ru.dmitrii.springcourse.SensorRestAPI.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dmitrii.springcourse.SensorRestAPI.dto.SensorDTO;
import ru.dmitrii.springcourse.SensorRestAPI.models.Sensor;
import ru.dmitrii.springcourse.SensorRestAPI.services.SensorService;

import java.util.List;

import static ru.dmitrii.springcourse.SensorRestAPI.dto.SensorDTO.collectErrorMessage;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;
    private final ModelMapper modelMapper;

    public SensorController(SensorService sensorService, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) collectErrorMessage(bindingResult);
        // if () todo collision by name...
        sensorService.save(convertToSensor(sensorDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}
