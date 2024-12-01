package ru.dmitrii.springcourse.SensorRestAPI;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import ru.dmitrii.springcourse.SensorRestAPI.dto.MeasurementsDTO;
import ru.dmitrii.springcourse.SensorRestAPI.dto.SensorDTO;

import java.util.*;

@SpringBootApplication
@EnableJpaRepositories("ru.dmitrii.springcourse.SensorRestAPI.repositories")
public class SensorRestAPI {

	public static void main(String[] args) {
		SpringApplication.run(SensorRestAPI.class, args);

		Scanner src = new Scanner(System.in);
		boolean on = true;
		while(on) {
			System.out.println(" 0 - create new sensor");
			System.out.println(" 1 - create thousand measurements");
			System.out.println(" 2 - get all measures");
			System.out.println("-1 - exit");
			switch(src.nextInt()) {
				case 0:
					createNewSensor();
					break;
				case 1:
					createThousandMeasures(); // 1000 random measures with selected by user sensor.
					break;
				case 2:
					getAllMeasures();
					break;
				case -1:
					System.out.println("Exit...");
					on = false;
			}
		}
		System.out.println("Finish app");
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void createNewSensor() {
		RestTemplate restTemplate = new RestTemplate();

		Map<String, String> jsonToSend = new HashMap<>();
		Scanner src = new Scanner(System.in);
		System.out.println("Please, input sensor name");
		String sensorName = src.nextLine();

		jsonToSend.put("name", sensorName);

		HttpEntity<Map<String, String>> request = new HttpEntity<>(jsonToSend);

		String url = "http://localhost:8080/sensors/registration";

		String post = restTemplate.postForObject(url, request, String.class);
		System.out.println(post);
	}

	public static void createThousandMeasures() {
		Random rnd = new Random();
		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object> jsonToSend = new HashMap<>();
		Scanner src = new Scanner(System.in);

		System.out.println("Please, input name of exist sensor");
		String sensorName = src.nextLine();

		for(int i = 0; i < 1000; i++) {
			float temperature = -100 + rnd.nextFloat(200);
			boolean raining = rnd.nextBoolean();

			jsonToSend.put("value", temperature);
			jsonToSend.put("raining", raining);
			jsonToSend.put("sensorDTO", new SensorDTO(sensorName));

			HttpEntity<Map<String, Object>> request = new HttpEntity<>(jsonToSend);

			String url = "http://localhost:8080/measurements/add";
			String post = restTemplate.postForObject(url, request, String.class);

			System.out.println(post);
		}
	}
	
	public static void getAllMeasures() {
		RestTemplate restTemplate = new RestTemplate();

		String url = "http://localhost:8080/measurements";
		String response = restTemplate.getForObject(url, String.class);
		System.out.println(response);
	}
	
}
