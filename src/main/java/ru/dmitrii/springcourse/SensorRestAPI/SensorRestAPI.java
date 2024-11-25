package ru.dmitrii.springcourse.SensorRestAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("ru.dmitrii.springcourse.SensorRestAPI.repositories")
public class SensorRestAPI {

	public static void main(String[] args) {
		SpringApplication.run(SensorRestAPI.class, args);
	}

}