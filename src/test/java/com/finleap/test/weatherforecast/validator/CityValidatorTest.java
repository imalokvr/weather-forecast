package com.finleap.test.weatherforecast.validator;

import com.finleap.test.weatherforecast.exception.InvalidCityException;
import org.junit.Test;

public class CityValidatorTest {

    @Test(expected = InvalidCityException.class)
    public void testGetAvgWhenCityNameNull() {
        CityValidator cityValidator = new CityValidator();
        cityValidator.validate(null);
    }

    @Test(expected = InvalidCityException.class)
    public void testGetAvgWhenCityNameIsEmpty() {
        CityValidator cityValidator = new CityValidator();
        cityValidator.validate("");
    }

}
