package com.leehyeonmin34.weather_reminder.domain.weather_info.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DataTypeCode {

    TEMP("Temp");

    private final String desc;


}
