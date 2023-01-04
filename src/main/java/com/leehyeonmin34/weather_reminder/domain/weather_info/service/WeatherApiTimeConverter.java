package com.leehyeonmin34.weather_reminder.domain.weather_info.service;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WeatherApiTimeConverter {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    static public LocalDateTime parse(String timeString){
        return LocalDateTime.parse(timeString, formatter);
    }

    static public String serialize(LocalDateTime localDateTime){
        return localDateTime.format(formatter);
    }

}
