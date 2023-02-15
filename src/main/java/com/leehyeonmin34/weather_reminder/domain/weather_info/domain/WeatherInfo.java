package com.leehyeonmin34.weather_reminder.domain.weather_info.domain;

import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherRegionConverter;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherApiTimeConverterForDB;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherDataTypeConverter;
import com.leehyeonmin34.weather_reminder.domain.weather_info.service.WeatherApiTimeConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeatherInfo implements Serializable {

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
    @Column(name = "region", nullable = false, updatable = false)
    private WeatherRegion weatherRegion;

    @Convert(converter = WeatherDataTypeConverter.class)
    @Column(name = "data_type", nullable = false, updatable = false)
    private WeatherDataType weatherDataType;

    @Column(name = "val", nullable = false, updatable = false)
    private float value;


    public WeatherInfo(LocalDateTime baseTime, LocalDateTime fcstTime, WeatherRegion weatherRegion, WeatherDataType weatherDataType, float value) {
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
