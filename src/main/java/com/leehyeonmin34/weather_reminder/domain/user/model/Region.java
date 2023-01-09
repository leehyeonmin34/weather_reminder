package com.leehyeonmin34.weather_reminder.domain.user.model;

import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
import com.leehyeonmin34.weather_reminder.global.error.exception.InvalidEnumCodeException;
import com.leehyeonmin34.weather_reminder.global.error.exception.UnhandledEnumTypeAtSwitchException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Region {
    // 날씨와 미세먼지 API에서 사용되는 지역 정보들을 중심적으로 연결, 관리해주는 enum

    SEOUL("서울", "000", WeatherRegion.SEOUL),
    BUSAN("부산", "XXX", WeatherRegion.BUSAN),
    ;

    private final String name;
    private final String code;
    private final WeatherRegion weatherRegion;

    public static Region of(final String code){
        return Arrays.stream(Region.values())
                .filter(item -> item.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new InvalidEnumCodeException(Region.class, code));
    }

    public static Region of(final WeatherRegion weatherRegion){
        return Arrays.stream(Region.values())
                .filter(item -> item.getWeatherRegion().equals(weatherRegion))
                .findAny()
                .orElseThrow(() -> new UnhandledEnumTypeAtSwitchException(Region.class, weatherRegion.getDongCode()));
    }

}
