package ru.dmitrii.springcourse.SensorRestAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dmitrii.springcourse.SensorRestAPI.models.Measurements;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurements, Integer> {

}
