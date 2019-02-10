package com.finleap.test.weatherforecast.controller.response;

import java.io.Serializable;

public class WeatherForecastResponse implements Serializable {

    private static final long serialVersionUID = 3118435570168628256L;

    String city;
    Average average;

    public WeatherForecastResponse() {

    }

    public WeatherForecastResponse(String city, Average average) {
        this.city = city;
        this.average = average;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Average getAverage() {
        return average;
    }

    public void setAverage(Average average) {
        this.average = average;
    }

}
