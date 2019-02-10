package com.finleap.test.weatherforecast;

import com.finleap.test.weatherforecast.exception.InvalidCityException;
import com.finleap.test.weatherforecast.exception.RemoteServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@ControllerAdvice
public class WeatherForcastAdvice {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(InvalidCityException.class)
    public void notFoundException(final InvalidCityException e , HttpServletResponse response) throws IOException {
        logError(e);
        response.sendError(HttpStatus.NOT_FOUND.value(),e.getMessage());
    }

    @ExceptionHandler(RemoteServiceException.class)
    public void notFoundException(final RemoteServiceException e , HttpServletResponse response) throws IOException {
        logError(e);
        response.sendError(HttpStatus.SERVICE_UNAVAILABLE.value(),e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public void notFoundException(final Exception e , HttpServletResponse response) throws IOException {
        logError(e);
        response.sendError(HttpStatus.SERVICE_UNAVAILABLE.value(),e.getMessage());
    }

    private void logError(final Exception exception) {
        final String message = Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());
        logger.error(message,exception);
    }

}
