package com.leehyeonmin34.weather_reminder.global.common.service;

import java.util.List;
import java.util.stream.Collectors;

public class StringJoiner {

    public static String join(List<String> list){

        return list.stream().filter(item -> !item.isEmpty())
                .collect(Collectors.joining(", "));
    }

}
