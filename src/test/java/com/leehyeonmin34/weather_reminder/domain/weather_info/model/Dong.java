package com.leehyeonmin34.weather_reminder.domain.weather_info.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Dong {

    SEOUL("서울", "1100000000");

    private final String desc;
    private final String dongCode;

}
