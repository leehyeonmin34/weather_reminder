package com.leehyeonmin34.weather_reminder.domain.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Entity;

@AllArgsConstructor
@Getter
@Entity
public class Notification {
    private String msg;

    private Long userId;
}
