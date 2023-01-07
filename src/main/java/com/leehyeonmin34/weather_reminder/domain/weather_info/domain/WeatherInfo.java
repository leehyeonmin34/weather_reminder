package com.leehyeonmin34.weather_reminder.domain.weather_info.domain;

import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherDataTypeConverter;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherRegionConverter;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "weather_info")
@ToString
public class WeatherInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "base_time", nullable = false, updatable = false)
    private String baseTime;

    @Column(name = "fcst_time", nullable = false, updatable = false)
    private String fcstTime;

    @Convert(converter = WeatherRegionConverter.class)
    @Column(name = "weather_region", nullable = false, updatable = false)
    private WeatherRegion weatherRegion;

    @Convert(converter = WeatherDataTypeConverter.class)
    @Column(name = "weather_data_type", nullable = false, updatable = false)
    private WeatherDataType weatherDataType;

    @Column(name = "unit", nullable = false, updatable = false)
    private String unit;

    @Column(name = "value", nullable = false, updatable = false)
    private float value;


    public WeatherInfo(String baseTime, String fcstTime, WeatherRegion weatherRegion, WeatherDataType weatherDataType, String unit, float value) {
        this.baseTime = baseTime;
        this.fcstTime = fcstTime;
        this.weatherRegion = weatherRegion;
        this.weatherDataType = weatherDataType;
        this.unit = unit;
        this.value = value;
    }

    protected WeatherInfo() {
    }
}
