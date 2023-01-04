package com.leehyeonmin34.weather_reminder.domain.weather_info.domain;

import com.leehyeonmin34.weather_reminder.domain.user.repository.RegionListConverter;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.Dong;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.DongConverter;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherDataTypeConverter;

import javax.persistence.*;

@Entity
public class WeatherInfo {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "base_time", nullable = false, updatable = false)
    private String baseTime;

    @Column(name = "fcst_time", nullable = false, updatable = false)
    private String fcstTime;

    @Convert(converter = DongConverter.class)
    @Column(name = "dong", nullable = false, updatable = false)
    private Dong dong;

    @Convert(converter = WeatherDataTypeConverter.class)
    @Column(name = "weather_data_type", nullable = false, updatable = false)
    private WeatherDataType weatherDataType;

    @Column(name = "unit", nullable = false, updatable = false)
    private String unit;

    @Column(name = "value", nullable = false, updatable = false)
    private float value;


    public WeatherInfo(String baseTime, String fcstTime, Dong dong, WeatherDataType weatherDataType, String unit, float value) {
        this.baseTime = baseTime;
        this.fcstTime = fcstTime;
        this.dong = dong;
        this.weatherDataType = weatherDataType;
        this.unit = unit;
        this.value = value;
    }

    protected WeatherInfo() {
    }
}
