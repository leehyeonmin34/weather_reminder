package com.leehyeonmin34.weather_reminder.global.message_q.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageType {

    NOTI("알림"),
    ;

    private final String desc;

}
