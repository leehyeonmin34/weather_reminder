package com.leehyeonmin34.weather_reminder.global.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DayTime {
    private final int hour;
    private final int minute;
}
