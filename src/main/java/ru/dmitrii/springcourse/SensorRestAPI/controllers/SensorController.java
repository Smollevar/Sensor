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

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;
    private final ModelMapper modelMapper;

    public SensorController(SensorService sensorService, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    // todo create method to send http query via RestTemplate...

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
        ResponseEntity re = null;
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

//    @ExceptionHandler
//    private ResponseEntity<SensorErrorResponse> handleExceptionAlreadyExist(SensorAlreadyExist e) {
//        SensorErrorResponse response = new SensorErrorResponse(
//                e.getMessage(),
//                System.currentTimeMillis()
//        );
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }

    public Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}
