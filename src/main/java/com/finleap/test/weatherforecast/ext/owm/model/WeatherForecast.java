package com.finleap.test.weatherforecast.ext.owm.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
                           "cod",
                           "message",
                           "cnt",
                           "list",
                           "city"
                   })
public class WeatherForecast {

    @JsonProperty("cod")
    private String cod;
    @JsonProperty("message")
    private Double message;
    @JsonProperty("cnt")
    private Long cnt;
    @JsonProperty("list")
    private List<Data> dataList = null;
    @JsonProperty("city")
    private City city;

    @JsonProperty("cod")
    public String getCod() {
        return cod;
    }

    @JsonProperty("cod")
    public void setCod(String cod) {
        this.cod = cod;
    }

    @JsonProperty("message")
    public Double getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(Double message) {
        this.message = message;
    }

    @JsonProperty("cnt")
    public Long getCnt() {
        return cnt;
    }

    @JsonProperty("cnt")
    public void setCnt(Long cnt) {
        this.cnt = cnt;
    }

    @JsonProperty("list")
    public List<Data> getDataList() {
        return dataList;
    }

    @JsonProperty("list")
    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
    }

    @JsonProperty("city")
    public City getCity() {
        return city;
    }

    @JsonProperty("city")
    public void  setCity(City city) {
        this.city = city;
    }

}
