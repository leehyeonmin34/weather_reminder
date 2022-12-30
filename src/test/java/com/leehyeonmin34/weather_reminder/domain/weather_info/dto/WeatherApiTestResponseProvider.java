package com.leehyeonmin34.weather_reminder.domain.weather_info.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@Component
@RequiredArgsConstructor
public class WeatherApiTestResponseProvider {

    private final ObjectMapper objectMapper;

    private final ResourceLoader resourceLoader;

    public WeatherApiResponseDto getSuccessResponse() throws IOException {
        return getResponseByPath("classpath:weather/weatherApiTestResponseJson.json");
    }

    public WeatherApiResponseDto getFailResponse() throws IOException {
        return getResponseByPath("classpath:weather/weatherApiTestFailResponseJson.json");
    }

    private WeatherApiResponseDto getResponseByPath(String path) throws IOException {
        File dataFile = resourceLoader.getResource(path).getFile();
        return objectMapper.readValue(dataFile, WeatherApiResponseDto.class);
    }

    public String getSuccessResponseAsString() throws IOException {
        return asString(getSuccessResponse());
    }

    public String getFailResponseAsString() throws IOException {
        return asString(getFailResponse());
    }

    private String asString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }



}
