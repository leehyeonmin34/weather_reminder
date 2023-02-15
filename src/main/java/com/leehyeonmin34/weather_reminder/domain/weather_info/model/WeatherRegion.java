package com.leehyeonmin34.weather_reminder.domain.weather_info.model;

import com.leehyeonmin34.weather_reminder.global.error.exception.InvalidEnumCodeException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum WeatherRegion implements Serializable {

    SEOUL("서울", "1100000000"),
    BUSAN("부산", "2600000000"),
    ;

    private final String desc;
    private final String dongCode;

    public static WeatherRegion of(final String code){
        return Arrays.stream(WeatherRegion.values())
                .filter(item -> item.getDongCode().equals(code))
                .findAny()
                .orElseThrow(() -> new InvalidEnumCodeException(WeatherRegion.class, code));
    }
}
