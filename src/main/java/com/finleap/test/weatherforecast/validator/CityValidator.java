package com.finleap.test.weatherforecast.validator;

import com.finleap.test.weatherforecast.exception.InvalidCityException;
import org.springframework.stereotype.Component;

@Component
public class CityValidator {

    public boolean validate(String city) {
        if(city != null && !city.isEmpty() && city.length() < 50) {
            return true;
        }else {
            throw new InvalidCityException("Invalid city name");
        }
    }
}
