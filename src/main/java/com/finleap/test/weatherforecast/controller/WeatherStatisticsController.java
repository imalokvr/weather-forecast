package com.finleap.test.weatherforecast.controller;

import com.finleap.test.weatherforecast.service.AvgWeatherForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherStatisticsController {

    @Autowired
    AvgWeatherForecastService avgWeatherForecastService;

    @GetMapping(value ="/data/{city}", produces = "application/json")
    public ResponseEntity getAvg(@PathVariable("city") String city ) {
        return ResponseEntity.ok(avgWeatherForecastService.getAvgTempAndPressure(city));
    }
}
