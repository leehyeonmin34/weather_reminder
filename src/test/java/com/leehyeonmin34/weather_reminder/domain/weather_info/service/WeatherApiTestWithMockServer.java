package com.leehyeonmin34.weather_reminder.domain.weather_info.service;

import com.leehyeonmin34.weather_reminder.domain.user.model.Region;
import com.leehyeonmin34.weather_reminder.domain.weather_info.dto.WeatherApiResponseDto;
import com.leehyeonmin34.weather_reminder.domain.weather_info.dto.WeatherApiResponseDtoTest;
import com.leehyeonmin34.weather_reminder.domain.weather_info.dto.WeatherApiTestResponseProvider;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType;
import com.leehyeonmin34.weather_reminder.global.api.exception.OpenApiException;
import com.leehyeonmin34.weather_reminder.global.config.RetryableRestTemplateConfiguration;
import com.leehyeonmin34.weather_reminder.global.test_config.IntegrationTest;
import com.leehyeonmin34.weather_reminder.global.test_config.ServiceTest;
import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.retry.ExhaustedRetryException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.Provider;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class WeatherApiTestWithMockServer extends IntegrationTest {

    private MockRestServiceServer mockServer;

    private WeatherApiService weatherApiService;

    @Autowired
    private RestTemplate restTemplate;

    @BeforeEach
    private void init(){
        mockServer = MockRestServiceServer.createServer(restTemplate);
        weatherApiService = new WeatherApiService(restTemplate);
    }

    @Autowired
    private WeatherApiTestResponseProvider weatherApiTestResponseProvider;

    private final String URL = weatherApiService.URL;
    private final Region region = Region.SEOUL;
    private final WeatherDataType weatherDataType = WeatherDataType.TEMP;

    @Test
    @DisplayName("날씨 API - 성공")
    public void WeatherApiTestSuccess() throws IOException {

        // GIVEN
        final String SUCCESS_REQUEST_URL = weatherApiService.getUriString(URL, region, weatherDataType);
        mockServer.expect(requestTo(SUCCESS_REQUEST_URL))
                .andRespond(
                        withSuccess(weatherApiTestResponseProvider.getSuccessResponseAsString(), MediaType.APPLICATION_JSON));

        // WHEN
        WeatherApiResponseDto response = weatherApiService.getApi(URL, region, weatherDataType);

        // THEN
        WeatherApiResponseDtoTest.validateContentSuccess(response);
    }

    @Test
    @DisplayName("날씨 API - 실패")
    public void WeatherApiTestFail() throws IOException {

        // GIVEN
        final String FAIL_URL = weatherApiService.HOST + "/fail";
        final String FAIL_REQUEST_URL = weatherApiService.getUriString(FAIL_URL, region, weatherDataType);
        mockServer.expect(requestTo(FAIL_REQUEST_URL))
                .andRespond(
                        withSuccess(weatherApiTestResponseProvider.getFailResponseAsString(), MediaType.APPLICATION_JSON));

        // WHEN
        WeatherApiResponseDto response = weatherApiService.getApi(FAIL_URL, region, weatherDataType);

        // THEN
        WeatherApiResponseDtoTest.validateContentFail(response);
    }

    @Test
    @Ignore
    @DisplayName("날씨 API - 타임아웃 재시도 횟수 초과")
    public void WeatherApiTestFailWithRecover() throws IOException, InterruptedException {

        // GIVEN
        final String FAIL_URL = weatherApiService.HOST;
        final String FAIL_REQUEST_URL = weatherApiService.getUriString(FAIL_URL, region, weatherDataType);
        mockServer.expect(requestTo(FAIL_REQUEST_URL))
                .andRespond(
                        withSuccess(timeoutResponse(), MediaType.APPLICATION_JSON));

        // WHEN - THEN
        Assertions.assertThatThrownBy(() -> weatherApiService.getApi(FAIL_URL, region, weatherDataType))
                .isInstanceOf(ExhaustedRetryException.class);
    }

    private String timeoutResponse() throws InterruptedException {
        Thread.sleep(61000);
        return "timeout";
    }

}