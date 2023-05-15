package com.leehyeonmin34.weather_reminder.domain.weather_info.domain;

import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherApiTimeConverterForDB;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherDataTypeConverter;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherRegionConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WeatherInfoPk implements Serializable {

    public final static Long serialVersionUID = 1L;

    @Convert(converter = WeatherRegionConverter.class)
    @Column(name = "weather_region", nullable = false, updatable = false)
    private WeatherRegion weatherRegion;

    @Convert(converter = WeatherApiTimeConverterForDB.class)
    @Column(name = "fcst_time", nullable = false, updatable = false)
    private LocalDateTime fcstTime;

    @Convert(converter = WeatherDataTypeConverter.class)
    @Column(name = "data_type", nullable = false, updatable = false)
    private WeatherDataType weatherDataType;

}
