package com.finleap.test.weatherforecast.ext.owm.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
                           "dt",
                           "main"
                   })
public class Data {

    @JsonProperty("dt")
    private Long timestamp;
    @JsonProperty("main")
    private Details details;

    @JsonProperty("dt")
    public Long getTimestamp() {
        return timestamp;
    }

    @JsonProperty("dt")
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty("main")
    public Details getDetails() {
        return details;
    }

    @JsonProperty("main")
    public void setDetails(Details details) {
        this.details = details;
    }

}
