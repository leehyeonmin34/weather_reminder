package com.leehyeonmin34.weather_reminder.domain.weather_info.dto;

import com.leehyeonmin34.weather_reminder.global.api.exception.OpenApiResponseStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class WeatherApiResponseDto {

    private Response response;

    @Getter
    @ToString
    public static class Response{
        private Header header;
        private Body body;
    }

    @Getter
    @ToString
    public static class Header{
        private String resultCode;
        private String resultMsg;
    }

    @Getter
    @ToString
    public static class Body{
        private String dataType;
        private Items items;
        private int pageNo;
        private int numOfRows;
        private int totalCount;
    }

    @Getter
    @ToString
    public static class Items{
        List<Item> item = new ArrayList<>();
    }

    @Getter
    @ToString
    public static class Item{
        private String baseTime;
        private String fcstTime;
        private float lon;
        private float lat;
        private String unit;
        private float value;
    }


}
