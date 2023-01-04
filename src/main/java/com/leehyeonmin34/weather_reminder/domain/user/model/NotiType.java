package com.leehyeonmin34.weather_reminder.domain.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum NotiType {

    RAIN("비", "NOTI_01"),
    SNOW("눈", "NOTI_02"),
    HOT("더운 날", "NOTI_03"),
    COLD("추운 날", "NOTI_04"),
    DUST("미세먼지", "NOTI_05")
    ;

    private String desc;
    private String code;

    public static NotiType of(final String code){
        return Arrays.stream(NotiType.values())
                .filter(item -> item.code.equals(code)).findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("선언되지 않은 알림 코드입니다. 요청된 알림코드 : %s", code)));
    }

}
