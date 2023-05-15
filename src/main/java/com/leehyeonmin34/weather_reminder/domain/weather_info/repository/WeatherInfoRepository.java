package com.leehyeonmin34.weather_reminder.domain.weather_info.repository;

import com.leehyeonmin34.weather_reminder.domain.weather_info.domain.WeatherInfo;
import com.leehyeonmin34.weather_reminder.domain.weather_info.domain.WeatherInfoPk;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WeatherInfoRepository extends JpaRepository<WeatherInfo, WeatherInfoPk> {

    default List<WeatherInfo> findAllTodayByWeatherRegion(final WeatherRegion weatherRegion){
        final LocalDateTime now = LocalDateTime.now();
        final int year = now.getYear();
        final int month = now.getMonthValue();
        final int day = now.getDayOfMonth();
        final LocalDateTime todayStart = LocalDateTime.of(year, month, day, 0, 0);
        final LocalDateTime todayEnd = LocalDateTime.of(year, month, day, 23, 59);
        return findAllByIdWeatherRegionAndIdFcstTimeBetween(weatherRegion, todayStart, todayEnd);
    }

    List<WeatherInfo> findAllByIdWeatherRegionAndIdFcstTimeBetween(final WeatherRegion weatherRegion, final LocalDateTime startTime, final LocalDateTime endTime);
}
