package ru.dmitrii.springcourse.SensorRestAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dmitrii.springcourse.SensorRestAPI.models.Sensor;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
}
