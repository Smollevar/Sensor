package ru.dmitrii.springcourse.SensorRestAPI.services;

import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dmitrii.springcourse.SensorRestAPI.models.Measurements;
import ru.dmitrii.springcourse.SensorRestAPI.models.Sensor;
import ru.dmitrii.springcourse.SensorRestAPI.repositories.MeasurementsRepository;

import java.util.List;

@Service
@Transactional
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository) {
        this.measurementsRepository = measurementsRepository;
    }

    public List<Measurements>findAll() {
        return measurementsRepository.findAll();
    }

    public void save(Measurements measurements) {
        Sensor sensor = measurements.getSensor();
        measurements.setSensor(sensor);
        sensor.getMeasurements().add(measurements);
//        sensor.setMeasurement(measurements);
        measurementsRepository.save(measurements);
        Hibernate.initialize(sensor); // todo sensor may be useful after saving measures...
    }


}
