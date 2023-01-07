package com.leehyeonmin34.weather_reminder.domain.weather_info.domain;

import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType;
<<<<<<< HEAD
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherRegionConverter;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherApiTimeConverterForDB;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherDataTypeConverter;
import com.leehyeonmin34.weather_reminder.domain.weather_info.service.WeatherApiTimeConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
=======
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherDataTypeConverter;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherRegionConverter;
import lombok.Getter;
>>>>>>> 589cb3d (날씨 정보 조회 배치 2)
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

<<<<<<< HEAD
@Entity(name = "WeatherInfo")
@Getter
@ToString
@Table(name = "weather_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
=======
@Entity
@Getter
@Table(name = "weather_info")
@ToString
>>>>>>> 589cb3d (날씨 정보 조회 배치 2)
public class WeatherInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = WeatherApiTimeConverterForDB.class)
    @Column(name = "base_time", nullable = false, updatable = false)
    private LocalDateTime baseTime;

    @Convert(converter = WeatherApiTimeConverterForDB.class)
    @Column(name = "fcst_time", nullable = false, updatable = false)
    private LocalDateTime fcstTime;

    @Convert(converter = WeatherRegionConverter.class)
<<<<<<< HEAD
    @Column(name = "region", nullable = false, updatable = false)
=======
    @Column(name = "weather_region", nullable = false, updatable = false)
>>>>>>> 589cb3d (날씨 정보 조회 배치 2)
    private WeatherRegion weatherRegion;

    @Convert(converter = WeatherDataTypeConverter.class)
    @Column(name = "data_type", nullable = false, updatable = false)
    private WeatherDataType weatherDataType;

    @Column(name = "val", nullable = false, updatable = false)
    private float value;


<<<<<<< HEAD
    public WeatherInfo(LocalDateTime baseTime, LocalDateTime fcstTime, WeatherRegion weatherRegion, WeatherDataType weatherDataType, float value) {
=======
    public WeatherInfo(String baseTime, String fcstTime, WeatherRegion weatherRegion, WeatherDataType weatherDataType, String unit, float value) {
>>>>>>> 589cb3d (날씨 정보 조회 배치 2)
        this.baseTime = baseTime;
        this.fcstTime = fcstTime;
        this.weatherRegion = weatherRegion;
        this.weatherDataType = weatherDataType;
        this.value = value;
    }

    public WeatherInfo(String baseTime, String fcstTime, WeatherRegion weatherRegion, WeatherDataType weatherDataType, float value) {
        this.baseTime = WeatherApiTimeConverter.parse(baseTime);
        this.fcstTime = WeatherApiTimeConverter.parse(fcstTime);
        this.weatherRegion = weatherRegion;
        this.weatherDataType = weatherDataType;
        this.value = value;
    }

}
