package com.leehyeonmin34.weather_reminder.global.common.model;

import org.springframework.stereotype.Component;

@Component
public class DayTimeConverter {

    static String serialize(final DayTime dayTime){
        assert dayTime != null;

        final String hour = String.valueOf(dayTime.getHour());
        final String minute = String.valueOf(dayTime.getMinute());
        return format(hour) + format(minute);
    }

    static DayTime deserialize(final String dayTimeString){
        assert dayTimeString.length() == 4;

        final int hour = Integer.valueOf(dayTimeString.substring(0, 2));
        final int minute = Integer.valueOf(dayTimeString.substring(2, 4));
        return new DayTime(hour, minute);
    }

    private static String format(final String value){
        assert value.length() > 0;

        return value.length() > 1 ? value : "0" + value;
    }

}
