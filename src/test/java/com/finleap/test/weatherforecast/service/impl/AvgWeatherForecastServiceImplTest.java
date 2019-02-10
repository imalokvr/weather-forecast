package com.finleap.test.weatherforecast.service.impl;

import com.finleap.test.weatherforecast.controller.response.WeatherForecastResponse;
import com.finleap.test.weatherforecast.exception.InvalidCityException;
import com.finleap.test.weatherforecast.ext.WeatherDataService;
import com.finleap.test.weatherforecast.ext.owm.model.City;
import com.finleap.test.weatherforecast.ext.owm.model.Data;
import com.finleap.test.weatherforecast.ext.owm.model.Details;
import com.finleap.test.weatherforecast.ext.owm.model.WeatherForecast;
import com.finleap.test.weatherforecast.validator.CityValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class AvgWeatherForecastServiceImplTest {

    @Mock
    WeatherDataService owmWeatherDataService;

    @Mock
    CityValidator cityValidator;

    WeatherForecast weatherForecast;

    @Spy
    @InjectMocks
    private AvgWeatherForecastServiceImpl avgWeatherForecastService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(avgWeatherForecastService);

    }

    @Test
    public void testGetAvgTempAndPressure() {

        String city = "test";
        Instant offset = Instant.ofEpochSecond(1549724400);
        Mockito.doReturn(OffsetDateTime.ofInstant(offset, ZoneId.of("Z"))).when(avgWeatherForecastService).getCurrentTime();
        weatherForecast = buildWeatherData(offset);
        Mockito.when(owmWeatherDataService.getData(city)).thenReturn(weatherForecast);

        WeatherForecastResponse weatherForecastResponse = avgWeatherForecastService.getAvgTempAndPressure(city);

        Assert.assertTrue(weatherForecastResponse.getAverage().getDayTemp().equals(23.0));
        Assert.assertTrue(weatherForecastResponse.getAverage().getNightTemp().equals(11.0));
        Assert.assertTrue(weatherForecastResponse.getAverage().getPressure().equals(17.0));
        Assert.assertEquals(city,weatherForecastResponse.getCity());
    }


    private WeatherForecast buildWeatherData(Instant offset) {
        WeatherForecast weatherForecast = new WeatherForecast();
        City city = new City();
        city.setName("Mumbai");
        weatherForecast.setCity(city);
        OffsetDateTime nowDate = OffsetDateTime.ofInstant(offset, ZoneId.of("Z"));
        List<Data> details = buildData(2,22,2,nowDate); // 22 + 24
        details.addAll(buildData(2,10,2,nowDate.plusHours(12))); // 10 + 12
        details.addAll(buildData(2,22,2,nowDate.plusHours(24))); // 22 + 24
        details.addAll(buildData(2,10,2,nowDate.plusHours(36))); // 10 + 12
        details.addAll(buildData(2,22,2,nowDate.plusDays(4))); // 22 + 24
        weatherForecast.setDataList(details);
        return weatherForecast;
    }

    private Details buildDetails(double temp,double pressure) {
        Details details  = new Details();
        details.setTemp(temp);
        details.setPressure(pressure);
        return details;
    }

    private List<Data> buildData(int count, int tempOffSet, int step, OffsetDateTime startDate) {
        List<Data> dataList = new ArrayList<>();
        int initialTemp = tempOffSet;
        for (int i = 0; i < count; i++) {
            Data data = new Data();
            data.setTimestamp(startDate.plusHours(step).toEpochSecond());
            data.setDetails(buildDetails(initialTemp,initialTemp));
            initialTemp += step;
            dataList.add(data);
        }
        return dataList;
    }

    @Test
    public void testIsInNextThreeDays() {
        long timestamp = 1549724400;
        Instant offset = Instant.ofEpochSecond(timestamp);
        OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(offset, ZoneId.of("Z"));
        Mockito.doReturn(offsetDateTime).when(avgWeatherForecastService).getCurrentTime();
        long valid_ts = offsetDateTime.plusHours(45).toEpochSecond();
        long invalid_ts_1 = offsetDateTime.plusHours(50).toEpochSecond();
        Assert.assertTrue(avgWeatherForecastService.isInNextThreeDays(valid_ts));
        Assert.assertTrue(avgWeatherForecastService.isInNextThreeDays(invalid_ts_1));
    }

}
