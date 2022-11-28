package com.leehyeonmin34.weather_reminder.domain.dust_info.repository;

import com.leehyeonmin34.weather_reminder.domain.dust_info.domain.DustInfo;
import com.leehyeonmin34.weather_reminder.domain.weather_info.domain.WeatherInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DustInfoRepository extends JpaRepository<DustInfo, Long> {
}
