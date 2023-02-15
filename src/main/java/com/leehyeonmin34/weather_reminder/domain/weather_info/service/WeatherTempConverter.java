package com.leehyeonmin34.weather_reminder.domain.weather_info.service;

public class WeatherTempConverter {

    static public String stringify(final float celcius) {
        if (celcius < 0) // -1.2 -> 영하 1.2도
            return "영하 " + -celcius + " 도";
        else
            return celcius + "도";
    }
}
