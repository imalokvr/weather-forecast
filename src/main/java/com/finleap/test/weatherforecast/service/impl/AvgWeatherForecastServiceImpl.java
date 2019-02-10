package com.finleap.test.weatherforecast.service.impl;

import com.finleap.test.weatherforecast.controller.response.Average;
import com.finleap.test.weatherforecast.controller.response.WeatherForecastResponse;
import com.finleap.test.weatherforecast.ext.WeatherDataService;
import com.finleap.test.weatherforecast.ext.owm.model.Data;
import com.finleap.test.weatherforecast.ext.owm.model.WeatherForecast;
import com.finleap.test.weatherforecast.service.AvgWeatherForecastService;
import com.finleap.test.weatherforecast.validator.CityValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AvgWeatherForecastServiceImpl implements AvgWeatherForecastService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    WeatherDataService owmWeatherDataService;

    @Autowired
    CityValidator cityValidator;

    @Override
    public WeatherForecastResponse getAvgTempAndPressure(String city) {

        cityValidator.validate(city);
        logger.debug("City validator finished successfully for : " + city );

        WeatherForecast forecast = owmWeatherDataService.getData(city);

        Map<Boolean, List<Data>> dayAndNightData = forecast.getDataList().stream()
                                                  .filter( data -> isInNextThreeDays(data.getTimestamp()))
                                                  .collect(Collectors.partitioningBy( data -> isDayTime(map(data.getTimestamp()).getHour())));

        Average avg = new Average();
        avg.setDayTemp(dayAndNightData.get(Boolean.TRUE).stream()
                                      .mapToDouble( r -> r.getDetails().getTemp()).average().orElse(Double.NaN));
        avg.setNightTemp(dayAndNightData.get(Boolean.FALSE).stream()
                                        .mapToDouble( r -> r.getDetails().getTemp()).average().orElse(Double.NaN));
        avg.setPressure(Stream.concat(dayAndNightData.get(Boolean.TRUE).stream(),dayAndNightData.get(Boolean.FALSE).stream())
                              .mapToDouble(r -> r.getDetails().getPressure()).average().orElse(Double.NaN));

        logger.info("Average calculated successfully for : " + city );
        return new WeatherForecastResponse(city,avg) ;
    }

    protected boolean isInNextThreeDays(long timestamp) {
        return map(timestamp).isBefore(getCurrentTime().plusDays(3));
    }

    protected OffsetDateTime getCurrentTime() {
        return OffsetDateTime.now(ZoneOffset.UTC);
    }

    protected OffsetDateTime map(long timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp);
        return OffsetDateTime.ofInstant(instant, ZoneId.of("Z"));
    }

    private boolean isDayTime(int hour) {
        return hour >= 6 && hour < 18;
    }
}
