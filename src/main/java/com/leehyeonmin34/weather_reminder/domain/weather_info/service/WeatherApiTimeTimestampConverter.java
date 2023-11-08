package com.leehyeonmin34.weather_reminder.domain.weather_info.service;

import net.bytebuddy.asm.Advice;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class WeatherApiTimeTimestampConverter{

    public static LocalDateTime parse(final Timestamp timestamp){
        return timestamp.toLocalDateTime();
    }

    public static Timestamp serialize(final LocalDateTime localDateTime){
        return Timestamp.valueOf(localDateTime);
    }

//    public static Timestamp serializeToDate(final LocalDateTime localDateTime) { return serialize(localDateTime).substring(0, 8); }
//
//    public static Timestamp serializeToTime(final LocalDateTime localDateTime) { return serialize(localDateTime).substring(8); }


}
