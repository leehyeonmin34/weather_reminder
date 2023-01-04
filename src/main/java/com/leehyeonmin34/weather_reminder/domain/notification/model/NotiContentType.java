package com.leehyeonmin34.weather_reminder.domain.notification.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotiContentType {

    HOT("더운 날"),
    COLD("추운 날"),
    RAIN("비"),
    DUST("미세 먼지"),
    ;

    private final String desc;

}
