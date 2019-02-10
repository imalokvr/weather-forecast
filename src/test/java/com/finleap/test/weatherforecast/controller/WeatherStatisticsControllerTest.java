package com.finleap.test.weatherforecast.controller;

import com.finleap.test.weatherforecast.WeatherForecastApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WeatherForecastApplication.class)
@WebAppConfiguration
public class WeatherStatisticsControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }


    @Test
    public void whenCityNameIsValid() throws Exception{
        this.mockMvc.perform(get("/data/mumbai").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(print());
    }


    @Test
    public void whenCityNameNotValid() throws Exception{
        this.mockMvc.perform(get("/data/mumbai123").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().is4xxClientError())
                    .andDo(print());
    }

}
