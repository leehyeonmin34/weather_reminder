package com.leehyeonmin34.weather_reminder.domain.weather_info.dto;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.leehyeonmin34.weather_reminder.WeatherReminderApplication;
import com.leehyeonmin34.weather_reminder.global.parent.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(classes = WeatherReminderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WeatherApiResponseDtoTest extends ServiceTest {

    @Autowired
    private ObjectMapper objectMapper = null;

    @Autowired
    private ResourceLoader resourceLoader = null;

    @DisplayName("날씨API_메시지_Json에서_Dto로변환(성공)")
    @Test
    public void weatherApiResponseDtoTest() throws IOException {

        // GIVEN
        Resource resource = resourceLoader.getResource("classpath:weather/weatherApiTestResponseJson.json");
        File dataFile = resource.getFile();

        // WHEN
        WeatherApiResponseDto dto = objectMapper.readValue(dataFile, WeatherApiResponseDto.class);

        // THEN
        validateContentSuccess(dto);
    }

    @DisplayName("날씨API_메시지_Json에서_Dto로변환(실패)")
    @Test
    public void weatherApiFailResponseDtoTest() throws IOException {
        // GIVEn
        Resource resource = resourceLoader.getResource("classpath:weather/weatherApiTestFailResponseJson.json");
        File dataFile = resource.getFile();

        // WHEN
        WeatherApiResponseDto dto = objectMapper.readValue(dataFile, WeatherApiResponseDto.class);

        // THEN
        validateContentFail(dto);
    }

    public static void validateContentSuccess(WeatherApiResponseDto dto){
        validateSuccessHeaderContent(dto);
        validateSuccessBodyContent(dto);
    }

    public static void validateContentFail(WeatherApiResponseDto dto){
        validateFailHeaderContent(dto);
        then(dto.getResponse().getBody()).isNull();
    }

    private static void validateSuccessHeaderContent(WeatherApiResponseDto dto){
        WeatherApiResponseDto.Header header = dto.getResponse().getHeader();
        then(header.getResultCode()).isEqualTo("00");
        then(header.getResultMsg()).isEqualTo("NORMAL_SERVICE");
    }

    private static void validateFailHeaderContent(WeatherApiResponseDto dto){
        WeatherApiResponseDto.Header header = dto.getResponse().getHeader();
        then(header.getResultCode()).isEqualTo("99");
        then(header.getResultMsg()).isEqualTo("최대 조회 기간은 오늘 기준으로 2일 전까지입니다.");
    }

    private static void validateSuccessBodyContent(WeatherApiResponseDto dto){

        // Body
        WeatherApiResponseDto.Body body = dto.getResponse().getBody();

        then(body.getDataType()).isEqualTo("JSON");
        then(body.getNumOfRows()).isEqualTo(10);
        then(body.getPageNo()).isEqualTo(1);
        then(body.getTotalCount()).isEqualTo(49);


        // Item
        WeatherApiResponseDto.Item item = body.getItems().getItem().get(0);

        then(item.getBaseTime()).isEqualTo("202212280300");
        then(item.getFcstTime()).isEqualTo("202212280300");
        then(item.getLat()).isEqualTo(37.56683F);
        then(item.getLon()).isEqualTo(126.97866F);
        then(item.getUnit()).isEqualTo("C");
        then(item.getValue()).isEqualTo(-4.3F);
    }


    public static void validateSuccess(WeatherApiResponseDto dto){
        validateSuccessHeader(dto);
        validateSuccessBody(dto);
    }

    public static void validateFail(WeatherApiResponseDto dto){
        validateFailHeader(dto);
        then(dto.getResponse().getBody()).isNull();
    }

    private static void validateSuccessHeader(WeatherApiResponseDto dto){
        WeatherApiResponseDto.Header header = dto.getResponse().getHeader();
        then(header.getResultCode()).isEqualTo("00");
    }

    private static void validateFailHeader(WeatherApiResponseDto dto){
        WeatherApiResponseDto.Header header = dto.getResponse().getHeader();
        then(header.getResultCode()).isNotEqualTo("00");
    }

    private static void validateSuccessBody(WeatherApiResponseDto dto){

        // Body
        WeatherApiResponseDto.Body body = dto.getResponse().getBody();
        then(body.getDataType()).isNotNull();
        then(body.getNumOfRows()).isNotNull();
        then(body.getPageNo()).isNotNull();
        then(body.getTotalCount()).isNotNull();


        // Item
        WeatherApiResponseDto.Item item = body.getItems().getItem().get(0);

        then(item.getBaseTime()).isNotNull();
        then(item.getFcstTime()).isNotNull();
        then(item.getLat()).isNotNull();
        then(item.getLon()).isNotNull();
        then(item.getUnit()).isNotNull();
        then(item.getValue()).isNotNull();
    }



}
