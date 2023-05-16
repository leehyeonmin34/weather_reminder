package com.leehyeonmin34.weather_reminder.domain.weather_info.domain;

import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherRegionConverter;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherApiTimeConverterForDB;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherDataTypeConverter;
import com.leehyeonmin34.weather_reminder.domain.weather_info.service.WeatherApiTimeConverter;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@ToString
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class WeatherInfo implements Serializable{

    @Getter
    @EmbeddedId
    private WeatherInfoPk id;

    @Getter
    @Convert(converter = WeatherApiTimeConverterForDB.class)
    @Column(name = "base_time", nullable = false, updatable = false)
    private LocalDateTime baseTime;

    @Getter
    @Column(name = "val", nullable = false, updatable = false)
    private float value;

    public WeatherInfo(LocalDateTime baseTime, LocalDateTime fcstTime, WeatherRegion weatherRegion, WeatherDataType weatherDataType, float value) {
        this.baseTime = baseTime;
        this.id = new WeatherInfoPk(weatherRegion, fcstTime,  weatherDataType);
        this.value = value;
    }

    public WeatherInfo(String baseTime, String fcstTime, WeatherRegion weatherRegion, WeatherDataType weatherDataType, float value) {
        this.baseTime = WeatherApiTimeConverter.parse(baseTime);
        this.id = new WeatherInfoPk(weatherRegion, WeatherApiTimeConverter.parse(fcstTime), weatherDataType);
        this.value = value;
    }

    public LocalDateTime getFcstTime(){
        return id.getFcstTime();
    }

    public WeatherRegion getWeatherRegion(){
        return id.getWeatherRegion();
    }

    public WeatherDataType getWeatherDataType(){
        return id.getWeatherDataType();
    }

}
