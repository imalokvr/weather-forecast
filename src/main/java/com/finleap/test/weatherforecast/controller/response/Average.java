package com.finleap.test.weatherforecast.controller.response;

import java.io.Serializable;

public class Average implements Serializable {

    private static final long serialVersionUID = 8280403478940598207L;

    Double dayTemp;
    Double nightTemp;
    Double pressure;

    public Double getDayTemp() {
        return dayTemp;
    }

    public Average setDayTemp(Double dayTemp) {
        this.dayTemp = dayTemp;
        return this;
    }

    public Double getNightTemp() {
        return nightTemp;
    }

    public void setNightTemp(Double nightTemp) {
        this.nightTemp = nightTemp;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }
}
