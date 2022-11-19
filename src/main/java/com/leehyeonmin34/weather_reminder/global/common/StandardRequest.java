package com.leehyeonmin34.weather_reminder.global.common;

import lombok.Getter;

@Getter
public class StandardRequest<T> {
    String accessToken;
    T requestDto;
}
