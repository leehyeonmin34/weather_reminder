package com.leehyeonmin34.weather_reminder.domain.weather_info.service;

import com.leehyeonmin34.weather_reminder.domain.user.model.Region;
import com.leehyeonmin34.weather_reminder.domain.weather_info.dto.WeatherApiResponseDto;
import com.leehyeonmin34.weather_reminder.domain.weather_info.dto.WeatherApiResponseDtoTest;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType;
import com.leehyeonmin34.weather_reminder.global.test_config.IntegrationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@Ignore
@Slf4j
public class WeahterApiTest extends IntegrationTest {

    @Autowired
    private WeatherApiService weatherApiService;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    @DisplayName("날씨 API")
    public void WeatherApiTestSuccess() throws IOException {

        // GIVEN
        final String URL = weatherApiService.URL;
        final Region region = Region.SEOUL;
        final WeatherDataType weatherDataType = WeatherDataType.TEMP;

        // WHEN
        WeatherApiResponseDto response = weatherApiService.getApi(URL, region, weatherDataType);
        log.info(response.toString());

        // THEN
        WeatherApiResponseDtoTest.validateSuccess(response);
    }


}
