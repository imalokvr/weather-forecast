package com.finleap.test.weatherforecast.ext;

import com.finleap.test.weatherforecast.ext.owm.model.WeatherForecast;

public interface WeatherDataService {

    WeatherForecast getData(String city);
}
