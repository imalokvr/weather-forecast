package com.finleap.test.weatherforecast.ext.owm;

import com.finleap.test.weatherforecast.exception.InvalidCityException;
import com.finleap.test.weatherforecast.exception.RemoteServiceException;
import com.finleap.test.weatherforecast.ext.owm.model.WeatherForecast;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
public class OWMWeatherDataServiceTest {

    @Spy
    @InjectMocks
    OWMWeatherDataService owmWeatherDataService;

    @Mock
    private RestTemplate restTemplate;

    private String url = "http://owm.org:8080/";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(owmWeatherDataService);
    }

    @Test
    public void testGetUrl() {
        Mockito.when(owmWeatherDataService.getOwmURL()).thenReturn(url);
        Mockito.when(owmWeatherDataService.getAppId()).thenReturn("123");
        String url = owmWeatherDataService.getUrl("mumbai");
        Assert.assertTrue(url.contains("q=mumbai"));
        Assert.assertTrue(url.contains("appid=123"));
        Assert.assertTrue(url.contains("units=metric"));
    }

    @Test
    public void testGetDataWhenCityExist() {

        String testURL = url + "Mumbai";
        Mockito.doReturn(testURL).when(owmWeatherDataService).getUrl("Mumbai");

        WeatherForecast weatherForecast = Mockito.mock(WeatherForecast.class);
        ResponseEntity<WeatherForecast> responseEntity = Mockito.mock(ResponseEntity.class);
        Mockito.when(responseEntity.getBody()).thenReturn(weatherForecast);
        Mockito.when(restTemplate.getForEntity(testURL,WeatherForecast.class)).thenReturn(responseEntity);

        WeatherForecast weatherForecastResponse = owmWeatherDataService.getData("Mumbai");
        Assert.assertEquals(weatherForecast,weatherForecastResponse);

    }

    @Test(expected = InvalidCityException.class)
    public void testGetDataWhenCityDoesNotExist() {

        String testURL = url + "Mumbai";
        Mockito.doReturn(testURL).when(owmWeatherDataService).getUrl("Mumbai");

        WeatherForecast weatherForecast = Mockito.mock(WeatherForecast.class);
        ResponseEntity<WeatherForecast> responseEntity = Mockito.mock(ResponseEntity.class);
        Mockito.when(responseEntity.getBody()).thenReturn(weatherForecast);
        Mockito.when(restTemplate.getForEntity(testURL,WeatherForecast.class)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        WeatherForecast weatherForecastResponse = owmWeatherDataService.getData("Mumbai");
        Assert.assertEquals(weatherForecast,weatherForecastResponse);

    }

    @Test(expected = RemoteServiceException.class)
    public void testGetDataWhenRequestIsUnAuthorised() {

        String testURL = url + "Mumbai";
        Mockito.doReturn(testURL).when(owmWeatherDataService).getUrl("Mumbai");

        WeatherForecast weatherForecast = Mockito.mock(WeatherForecast.class);
        ResponseEntity<WeatherForecast> responseEntity = Mockito.mock(ResponseEntity.class);
        Mockito.when(responseEntity.getBody()).thenReturn(weatherForecast);
        Mockito.when(restTemplate.getForEntity(testURL,WeatherForecast.class)).thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));

        WeatherForecast weatherForecastResponse = owmWeatherDataService.getData("Mumbai");
        Assert.assertEquals(weatherForecast,weatherForecastResponse);

    }
}
