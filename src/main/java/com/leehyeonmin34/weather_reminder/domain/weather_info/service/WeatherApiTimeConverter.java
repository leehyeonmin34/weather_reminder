package com.leehyeonmin34.weather_reminder.domain.weather_info.service;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WeatherApiTimeConverter {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    static public LocalDateTime parse(final String timeString){
        return LocalDateTime.parse(timeString, formatter);
    }

    static public String serialize(final LocalDateTime localDateTime){
        return localDateTime.format(formatter);
    }

    static public String serializeToDate(final LocalDateTime localDateTime) { return serialize(localDateTime).substring(0, 8); }

    static public String serializeToTime(final LocalDateTime localDateTime) { return serialize(localDateTime).substring(8); }

}
