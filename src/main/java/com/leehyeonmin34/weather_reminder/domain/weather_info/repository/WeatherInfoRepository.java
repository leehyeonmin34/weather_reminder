package com.leehyeonmin34.weather_reminder.domain.weather_info.repository;

import com.leehyeonmin34.weather_reminder.domain.weather_info.domain.WeatherInfo;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface WeatherInfoRepository extends JpaRepository<WeatherInfo, Long> {

    default List<WeatherInfo> findAllTodayByWeatherRegion(WeatherRegion weatherRegion){
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        LocalDateTime todayStart = LocalDateTime.of(year, month, day, 0, 0);
        LocalDateTime todayEnd = LocalDateTime.of(year, month, day, 23, 59);
        return findAllByWeatherRegionAndFcstTimeBetween(weatherRegion, todayStart, todayEnd);
    }

    List<WeatherInfo> findAllByWeatherRegionAndFcstTimeBetween(WeatherRegion weatherRegion, LocalDateTime startTime, LocalDateTime endTime);
}
