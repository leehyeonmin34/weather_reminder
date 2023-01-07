package com.leehyeonmin34.weather_reminder.domain.weather_info.service;

import com.leehyeonmin34.weather_reminder.domain.weather_info.dto.WeatherApiResponseDto;
import com.leehyeonmin34.weather_reminder.domain.weather_info.dto.WeatherApiResponseDtoTest;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType;
import com.leehyeonmin34.weather_reminder.global.test_config.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class WeahterApiTest extends IntegrationTest {

    @Autowired
    private WeatherApiService weatherApiService;

    @Test
    @DisplayName("날씨 API")
    public void WeatherApiTestSuccess() throws IOException {

        // GIVEN
        final String URL = weatherApiService.URL;
        final WeatherRegion weatherRegion = WeatherRegion.SEOUL;
        final WeatherDataType weatherDataType = WeatherDataType.TEMP;

        // WHEN
        WeatherApiResponseDto response = weatherApiService.getApi(URL, weatherRegion, weatherDataType);

        // THEN
        System.out.println(response);
        WeatherApiResponseDtoTest.validateSuccess(response);
    }
}
