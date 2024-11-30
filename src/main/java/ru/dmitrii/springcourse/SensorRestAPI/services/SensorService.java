package ru.dmitrii.springcourse.SensorRestAPI.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dmitrii.springcourse.SensorRestAPI.models.Sensor;
import ru.dmitrii.springcourse.SensorRestAPI.repositories.SensorRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SensorService {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public Sensor findByName(String name) {
        Optional <Sensor> optional = sensorRepository.findByName(name);
        return optional.orElse(null);
    }

    public List<Sensor> getAllSensors(){
        return sensorRepository.findAll();
    }

    public void save(Sensor sensor) {
        // todo probably need enrich object timestamp.
        sensorRepository.save(sensor);
    }

}
