package ru.dmitrii.springcourse.SensorRestAPI.services;

import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dmitrii.springcourse.SensorRestAPI.dto.MeasurementsDTO;
import ru.dmitrii.springcourse.SensorRestAPI.dto.SensorDTO;
import ru.dmitrii.springcourse.SensorRestAPI.models.Measurements;
import ru.dmitrii.springcourse.SensorRestAPI.models.Sensor;
import ru.dmitrii.springcourse.SensorRestAPI.repositories.MeasurementsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, ModelMapper modelMapper) {
        this.measurementsRepository = measurementsRepository;
        this.modelMapper = modelMapper;
    }

    public List<Measurements>findAll() {
        return measurementsRepository.findAll();
    }

    public List<MeasurementsDTO> getAllMeasurementsDTO() {
        List<Measurements> measurements = findAll(); // measurementsService.
        List<MeasurementsDTO> measRet = new ArrayList<>();

        for(int i = 0; i < measurements.size(); i++) {
            measRet.add(modelMapper.map(measurements.get(i), MeasurementsDTO.class));
            SensorDTO sdto = new SensorDTO();
            sdto.setName(measurements.get(i).getSensor().getName());
            measRet.get(i).setSensorDTO(sdto);
        }
        return measRet;
    }

    public String getRainyDays() {
        List<Measurements> measurements = measurementsRepository.findAll();
        int days = 0;
        for(Measurements m : measurements) {
            if (m.isRaining()) ++days;
        }
        return "Raining days: " + days;
    }

    public void save(Measurements measurements) {
        Sensor sensor = measurements.getSensor();
        measurements.setSensor(sensor);
        sensor.getMeasurements().add(measurements);
        measurementsRepository.save(measurements);
        Hibernate.initialize(sensor); // todo sensor may be useful after saving measures...
    }

}
