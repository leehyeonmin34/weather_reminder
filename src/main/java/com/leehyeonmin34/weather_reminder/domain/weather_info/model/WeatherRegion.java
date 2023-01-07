package com.leehyeonmin34.weather_reminder.domain.weather_info.model;

<<<<<<< HEAD
import com.leehyeonmin34.weather_reminder.domain.user.model.Region;
=======
>>>>>>> 589cb3d (날씨 정보 조회 배치 2)
import com.leehyeonmin34.weather_reminder.global.error.exception.InvalidEnumCodeException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum WeatherRegion {

    SEOUL("서울", "1100000000"),
    BUSAN("부산", "2600000000"),
    ;

    private final String desc;
    private final String dongCode;

<<<<<<< HEAD
    public static WeatherRegion of(final String code){
=======
    public static WeatherRegion of(String code){
>>>>>>> 589cb3d (날씨 정보 조회 배치 2)
        return Arrays.stream(WeatherRegion.values())
                .filter(item -> item.getDongCode().equals(code))
                .findAny()
                .orElseThrow(() -> new InvalidEnumCodeException(WeatherRegion.class, code));
    }
}
