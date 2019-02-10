package com.finleap.test.weatherforecast.service;


import com.finleap.test.weatherforecast.controller.response.WeatherForecastResponse;

public interface AvgWeatherForecastService {

   WeatherForecastResponse getAvgTempAndPressure(String city);
}
