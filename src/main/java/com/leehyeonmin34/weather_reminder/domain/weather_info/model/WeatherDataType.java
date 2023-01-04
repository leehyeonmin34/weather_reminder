package com.leehyeonmin34.weather_reminder.domain.weather_info.model;

import com.leehyeonmin34.weather_reminder.global.api.exception.OpenApiResponseStatus;
import com.leehyeonmin34.weather_reminder.global.error.exception.InvalidEnumCodeException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum WeatherDataType {

    TEMP("온도", "Temp", "C"),
    RAIN("강수량", "Rain", "mm"),

    ;

    private final String desc;
    private final String code;
    private final String unit;

    public static WeatherDataType of(String code){
        return Arrays.stream(WeatherDataType.values())
                .filter(item -> item.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new InvalidEnumCodeException(WeatherDataType.class, code));
    }
}
