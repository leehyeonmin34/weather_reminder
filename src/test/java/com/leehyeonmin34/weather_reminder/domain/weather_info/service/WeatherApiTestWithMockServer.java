package com.leehyeonmin34.weather_reminder.domain.weather_info.service;

import com.leehyeonmin34.weather_reminder.domain.weather_info.dto.WeatherApiResponseDto;
import com.leehyeonmin34.weather_reminder.domain.weather_info.dto.WeatherApiResponseDtoTest;
import com.leehyeonmin34.weather_reminder.domain.weather_info.dto.WeatherApiTestResponseProvider;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.Dong;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType;
import com.leehyeonmin34.weather_reminder.global.parent.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;

import java.io.IOException;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(SpringExtension.class)
@RestClientTest(value = {WeatherApiService.class, WeatherApiTestResponseProvider.class})
public class WeatherApiTestWithMockServer extends ServiceTest {


    @Autowired
    private MockRestServiceServer mockServer;

    @Autowired
    private WeatherApiService weatherApiService;

    @Autowired
    private WeatherApiTestResponseProvider weatherApiTestResponseProvider;

    private final String URL = weatherApiService.URL;
    private final Dong dong = Dong.SEOUL;
    private final WeatherDataType weatherDataType = WeatherDataType.TEMP;

    @Test
    @DisplayName("날씨 API - 성공")
    public void WeatherApiTestSuccess() throws IOException {

        // GIVEN
        final String SUCCESS_REQUEST_URL = weatherApiService.getUriString(URL, dong, weatherDataType);
        mockServer.expect(requestTo(SUCCESS_REQUEST_URL))
                .andRespond(
                        withSuccess(weatherApiTestResponseProvider.getSuccessResponseAsString(), MediaType.APPLICATION_JSON));

        // WHEN
        WeatherApiResponseDto response = weatherApiService.getApi(URL, dong, weatherDataType);

        // THEN
        WeatherApiResponseDtoTest.validateContentSuccess(response);
    }

    @Test
    @DisplayName("날씨 API - 실패")
    public void WeatherApiTestFail() throws IOException {

        // GIVEN
        final String FAIL_URL = weatherApiService.HOST + "/fail";
        final String FAIL_REQUEST_URL = weatherApiService.getUriString(FAIL_URL, dong, weatherDataType);
        mockServer.expect(requestTo(FAIL_REQUEST_URL))
                .andRespond(
                        withSuccess(weatherApiTestResponseProvider.getFailResponseAsString(), MediaType.APPLICATION_JSON));

        // WHEN
        WeatherApiResponseDto response = weatherApiService.getApi(FAIL_URL, dong, weatherDataType);

        // THEN
        WeatherApiResponseDtoTest.validateContentFail(response);
    }

}