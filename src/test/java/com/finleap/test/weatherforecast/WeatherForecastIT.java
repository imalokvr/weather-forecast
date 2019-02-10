package com.finleap.test.weatherforecast;

import com.finleap.test.weatherforecast.controller.response.WeatherForecastResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeatherForecastApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WeatherForecastIT {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	private String getURL(String uri) {
		return "http://localhost:" + port + uri;
	}

	@Test
	public void testWhenValidCityNameProvided() {
		ResponseEntity<WeatherForecastResponse> response = restTemplate.getForEntity(
				getURL("/data/mumbai"), WeatherForecastResponse.class);

		Assert.assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		Assert.assertTrue(response.getBody().getAverage().getDayTemp() > 0.0);
		Assert.assertTrue(response.getBody().getAverage().getNightTemp() > 0.0);
		Assert.assertTrue(response.getBody().getAverage().getPressure() > 0.0);
	}

	@Test
	public void testWhenInValidCityNameProvided() {

		ResponseEntity<WeatherForecastResponse> response = restTemplate.getForEntity(
				getURL("/data/mumbai12"), WeatherForecastResponse.class);
		Assert.assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
	}
}

