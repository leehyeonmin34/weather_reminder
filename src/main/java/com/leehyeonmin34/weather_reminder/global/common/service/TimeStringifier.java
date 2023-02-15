package com.leehyeonmin34.weather_reminder.global.common.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TimeStringifier {

    // 같은 날이라고 가정하고 시간(hour)만 비교해서 오전 오후 시간 문자열 반환
    // ex) 오전 9시, 10시, 오후 1시, 2시
    static public String stringifyByNoon(final List<LocalDateTime> timeList){
        Stream<LocalDateTime> sorted = timeList.stream().sorted(Comparator.comparing(LocalDateTime::getHour));

        List<LocalDateTime> am = new ArrayList<>();
        List<LocalDateTime> pm = new ArrayList<>();
        sorted.forEach(item -> {
            final int hour = item.getHour();
            if( hour < 12 ) am.add(item);
            else pm.add(item);
        });

        final String amHours = stringifyByOnlyHour(am);
        final String pmHours = stringifyByOnlyHour(pm);

        final String amFinal = amHours.isEmpty() ? "" : "오전 " + amHours;
        final String pmFinal = pmHours.isEmpty() ? "" : "오후 " + pmHours;

        return StringJoiner.join(List.of(amFinal, pmFinal));
    }

    private static String stringifyByOnlyHour(final List<LocalDateTime> timeList){
        return timeList.stream().map(item -> extractHourByNoon(item) + "시")
                .collect(Collectors.joining(", "));
    }

    private static int extractHourByNoon(final LocalDateTime time){
        final int hour = time.getHour();
        return hour < 13 ? hour : hour - 12;
    }


}
