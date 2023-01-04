package com.leehyeonmin34.weather_reminder.domain.weather_info.repository;

import com.leehyeonmin34.weather_reminder.domain.weather_info.domain.WeatherInfo;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface WeatherInfoRepository extends JpaRepository<WeatherInfo, Long> {

    default List<WeatherInfo> findAllTodayByWeatherRegion(final WeatherRegion weatherRegion){
        final LocalDateTime now = LocalDateTime.now();
        final int year = now.getYear();
        final int month = now.getMonthValue();
        final int day = now.getDayOfMonth();
        final LocalDateTime todayStart = LocalDateTime.of(year, month, day, 0, 0);
        final LocalDateTime todayEnd = LocalDateTime.of(year, month, day, 23, 59);
        return findAllByWeatherRegionAndFcstTimeBetween(weatherRegion, todayStart, todayEnd);
    }

    List<WeatherInfo> findAllByWeatherRegionAndFcstTimeBetween(final WeatherRegion weatherRegion, final LocalDateTime startTime, final LocalDateTime endTime);
}
