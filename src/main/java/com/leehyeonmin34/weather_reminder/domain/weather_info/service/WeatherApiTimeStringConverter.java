package com.leehyeonmin34.weather_reminder.domain.weather_info.service;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WeatherApiTimeStringConverter{

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    public static LocalDateTime parse(final String javaType){
        return LocalDateTime.parse(javaType, formatter);
    }

    public static String serialize(final LocalDateTime localDateTime){
        return localDateTime.format(formatter);
    }

    public static String serializeToDate(final LocalDateTime localDateTime) { return serialize(localDateTime).substring(0, 8); }

    public static String serializeToTime(final LocalDateTime localDateTime) { return serialize(localDateTime).substring(8); }


}
