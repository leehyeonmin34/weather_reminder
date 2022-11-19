package com.leehyeonmin34.weather_reminder.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Region {

    SEOUL("서울", "000"),
    JEJU("제주", "999")
    ;

    private String name;
    private String code;

    public static Region of(String code){
        return Arrays.stream(Region.values())
                .filter(item -> item.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("지역 코드 %s에 해당하는 지역이 없습니다.",code)));
    }

}
