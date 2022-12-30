package com.leehyeonmin34.weather_reminder.domain.weather_info.model;

import com.leehyeonmin34.weather_reminder.global.api.exception.OpenApiResponseStatus;
import com.leehyeonmin34.weather_reminder.global.error.exception.InvalidEnumCodeException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum Dong {

    SEOUL("서울", "1100000000");

    private final String desc;
    private final String dongCode;

    public static Dong of(String code){
        return Arrays.stream(Dong.values())
                .filter(item -> item.getDongCode().equals(code))
                .findAny()
                .orElseThrow(() -> new InvalidEnumCodeException(Dong.class, code));
    }
}
