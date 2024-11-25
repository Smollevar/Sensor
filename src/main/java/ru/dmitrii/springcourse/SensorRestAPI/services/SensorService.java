package ru.dmitrii.springcourse.SensorRestAPI.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dmitrii.springcourse.SensorRestAPI.models.Sensor;
import ru.dmitrii.springcourse.SensorRestAPI.repositories.SensorRepository;

@Service
@Transactional
public class SensorService {

    /*
    methods:
    todo get by name from db and compare with inputed.
     */

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public void save(Sensor sensor) {
        // todo probably need enrich object timestamp.
        sensorRepository.save(sensor);
    }

}
