package com.leehyeonmin34.weather_reminder.domain.weather_info.model;

import com.leehyeonmin34.weather_reminder.domain.user.model.Region;
import com.leehyeonmin34.weather_reminder.global.error.exception.InvalidEnumCodeException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum WeatherRegion {

    SEOUL("서울", "1100000000"),
    BUSAN("부산", "XXX"),
    ;

    private final String desc;
    private final String dongCode;

    public static WeatherRegion of(String code){
        return Arrays.stream(WeatherRegion.values())
                .filter(item -> item.getDongCode().equals(code))
                .findAny()
                .orElseThrow(() -> new InvalidEnumCodeException(WeatherRegion.class, code));
    }

    public static WeatherRegion of(Region region){
        return Arrays.stream(WeatherRegion.values())
                .filter(item -> item.getDesc().equals(region.getName()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("%s에 해당하는 지역이 없습니다.", region)));
    }
}
