package com.leehyeonmin34.weather_reminder.domain.weather_info.domain;

import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.DongConverter;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherApiTimeConverterForDB;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherDataTypeConverter;
import com.leehyeonmin34.weather_reminder.domain.weather_info.service.WeatherApiTimeConverter;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
public class WeatherInfo {

    @Id
    @GeneratedValue
    private Long id;

    @Convert(converter = WeatherApiTimeConverterForDB.class)
    @Column(name = "base_time", nullable = false, updatable = false)
    private LocalDateTime baseTime;

    @Convert(converter = WeatherApiTimeConverterForDB.class)
    @Column(name = "fcst_time", nullable = false, updatable = false)
    private LocalDateTime fcstTime;

    @Convert(converter = DongConverter.class)
    @Column(name = "weather_region", nullable = false, updatable = false)
    private WeatherRegion weatherRegion;

    @Convert(converter = WeatherDataTypeConverter.class)
    @Column(name = "weather_data_type", nullable = false, updatable = false)
    private WeatherDataType weatherDataType;

    @Column(name = "value", nullable = false, updatable = false)
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

    protected WeatherInfo() {
    }
}
