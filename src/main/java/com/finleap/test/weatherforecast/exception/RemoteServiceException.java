package com.finleap.test.weatherforecast.exception;

public class RemoteServiceException extends RuntimeException {

    public RemoteServiceException(Exception e) {
        super(e);
    }
}
