package ru.dmitrii.springcourse.SensorRestAPI.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.dmitrii.springcourse.SensorRestAPI.dto.SensorDTO;
import ru.dmitrii.springcourse.SensorRestAPI.models.Sensor;
import ru.dmitrii.springcourse.SensorRestAPI.services.SensorService;
import ru.dmitrii.springcourse.SensorRestAPI.util.SensorAlreadyExist;
import ru.dmitrii.springcourse.SensorRestAPI.util.SensorErrorResponse;
import ru.dmitrii.springcourse.SensorRestAPI.util.SensorNotCreatedException;
import ru.dmitrii.springcourse.SensorRestAPI.util.SensorParentException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;
    private final ModelMapper modelMapper;

    public SensorController(SensorService sensorService, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<SensorDTO> getAllSensors() {
        List<Sensor> sensors = sensorService.getAllSensors();
        List<SensorDTO> sensorDTOs = new ArrayList<>();
        for(Sensor sensor : sensors) {
            sensorDTOs.add(modelMapper.map(sensor, SensorDTO.class));
        }
        return sensorDTOs;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) throw SensorDTO.collectErrorMessage(bindingResult);

        if (sensorService.findByName(sensorDTO.getName()) != null)
            throw new SensorAlreadyExist("There is already a sensor with that name");

        sensorService.save(convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleExceptionNotCreated(SensorParentException e) {
        return SensorDTO.handleExceptionNotCreatedDTO(e);
    }

    public Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}
