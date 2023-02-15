package com.leehyeonmin34.weather_reminder.domain.weather_info.model;

import com.leehyeonmin34.weather_reminder.domain.weather_info.domain.WeatherInfo;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class WeatherInfoList implements Serializable {

    private final List<WeatherInfo> tempWeatherInfoList;

    private final List<WeatherInfo> rainWeatherInfoList;

    private final WeatherRegion weatherRegion;

    public WeatherInfoList(final WeatherRegion weatherRegion, final List<WeatherInfo> weatherInfoList){
        this.weatherRegion = weatherRegion;

        this.tempWeatherInfoList = weatherInfoList.stream()
                .filter(item -> item.getWeatherDataType() == WeatherDataType.TEMP)
                .sorted(Comparator.comparing(WeatherInfo::getFcstTime))
                .collect(Collectors.toUnmodifiableList());

        this.rainWeatherInfoList = weatherInfoList.stream()
                .filter(item -> item.getWeatherDataType() == WeatherDataType.RAIN)
                .sorted(Comparator.comparing(WeatherInfo::getFcstTime))
                .collect(Collectors.toUnmodifiableList());
    }

}
