package com.leehyeonmin34.weather_reminder.domain.user.model;

import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
import com.leehyeonmin34.weather_reminder.global.error.exception.InvalidEnumCodeException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Region {

    SEOUL("서울", "000", WeatherRegion.SEOUL),
    BUSAN("부산", "XXX", WeatherRegion.BUSAN),
    ;

    private String name;
    private String code;
    private WeatherRegion weatherRegion;

    public static Region of(String code){
        return Arrays.stream(Region.values())
                .filter(item -> item.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new InvalidEnumCodeException(Region.class, code));
    }

    public static Region of(WeatherRegion weatherRegion){
        return Arrays.stream(Region.values())
                .filter(item -> item.getName().equals(weatherRegion.getDesc()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("%s에 해당하는 지역이 없습니다.", weatherRegion)));
    }

}
