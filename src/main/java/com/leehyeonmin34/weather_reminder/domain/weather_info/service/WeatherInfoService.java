package com.leehyeonmin34.weather_reminder.domain.weather_info.service;

import com.leehyeonmin34.weather_reminder.domain.weather_info.domain.WeatherInfo;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherInfoList;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherInfoRepository;
import com.leehyeonmin34.weather_reminder.global.cache.config.CacheEnv;
import com.leehyeonmin34.weather_reminder.global.cache.service.CacheModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class WeatherInfoService {

    private final WeatherInfoRepository weatherInfoRepository;

    private final CacheModule cacheModule;

    public WeatherInfoList getWeatherInfoListToday(final WeatherRegion weatherRegion){

        // 캐시 조회를 위한 key. 지역코드 + 날짜
        final String key = weatherRegion.getDongCode() + WeatherApiTimeConverter.serializeToDate(LocalDateTime.now());

        // 날짜를 key로 해당 날짜&지역의 모든 날씨 정보를 조회
        return cacheModule.getCacheOrLoad(CacheEnv.TODAY_WEATHER_INFO_LIST
                , key
                , (_key) -> loadWeatherInfoListToday(weatherRegion));
    }

    private WeatherInfoList loadWeatherInfoListToday(final WeatherRegion weatherRegion){
        final List<WeatherInfo> weatherInfoListToday = weatherInfoRepository.findAllTodayByWeatherRegion(weatherRegion);
        return new WeatherInfoList(weatherRegion, weatherInfoListToday);
    }

}
