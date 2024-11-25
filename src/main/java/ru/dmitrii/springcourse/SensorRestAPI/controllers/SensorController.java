package ru.dmitrii.springcourse.SensorRestAPI.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    @PostMapping("/registration")
    public void registration() {

    }    // TODO replace return value with action code...
}
