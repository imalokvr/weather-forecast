package com.finleap.test.weatherforecast.exception;

public class InvalidCityException extends RuntimeException {

    public InvalidCityException(String message) {
        super(message);
    }
}
