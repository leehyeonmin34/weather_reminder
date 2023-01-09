package com.leehyeonmin34.weather_reminder.domain.weather_info.service;

import com.leehyeonmin34.weather_reminder.domain.weather_info.domain.WeatherInfo;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherInfoList;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class WeatherInfoService {

    private final WeatherInfoRepository weatherInfoRepository;

    public WeatherInfoList getWeatherInfoListToday(final WeatherRegion weatherRegion){

        final List<WeatherInfo> weatherInfoListToday = weatherInfoRepository.findAllTodayByWeatherRegion(weatherRegion);

        return new WeatherInfoList(weatherRegion, weatherInfoListToday);
    }

}
