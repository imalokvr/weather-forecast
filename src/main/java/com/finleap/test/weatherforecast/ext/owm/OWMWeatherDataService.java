package com.finleap.test.weatherforecast.ext.owm;

import com.finleap.test.weatherforecast.exception.InvalidCityException;
import com.finleap.test.weatherforecast.exception.RemoteServiceException;
import com.finleap.test.weatherforecast.ext.WeatherDataService;
import com.finleap.test.weatherforecast.ext.owm.model.WeatherForecast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service("owmWeatherDataService")
public class OWMWeatherDataService implements WeatherDataService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    @Value("${owm.url}")
    private String owmURL;

    @Value("${owm.app.id}")
    private String appId;

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public String getOwmURL() {
        return owmURL;
    }

    public String getAppId() {
        return appId;
    }


    @Override
    public WeatherForecast getData(String city) {
        try {
            ResponseEntity<WeatherForecast> response = getRestTemplate().getForEntity(getUrl(city), WeatherForecast.class);
            logger.info("Weather data for city : " + city + " received successfully");
            return response.getBody();
        }catch (HttpClientErrorException | HttpServerErrorException e ) {
            if(HttpStatus.NOT_FOUND.equals(e.getStatusCode())) {
                throw new InvalidCityException("City not found");
            }
            throw new RemoteServiceException(e);
        }
    }

    protected String getUrl(String city) throws RemoteServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getOwmURL()).queryParam("appid", getAppId())
                                          .queryParam("q", URLEncoder.encode(city, "UTF-8"))
                                          .queryParam("units", "metric");
            return builder.toUriString();
        } catch (UnsupportedEncodingException e) {
            throw new RemoteServiceException(e);
        }
    }
}
