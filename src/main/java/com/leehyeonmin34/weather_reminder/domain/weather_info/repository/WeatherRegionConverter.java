package com.leehyeonmin34.weather_reminder.domain.weather_info.repository;

import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
@Component
public class WeatherRegionConverter implements AttributeConverter<WeatherRegion, String> {

    @Override
<<<<<<< HEAD
    public String convertToDatabaseColumn(final WeatherRegion attribute) {
=======
    public String convertToDatabaseColumn(WeatherRegion attribute) {
>>>>>>> 589cb3d (날씨 정보 조회 배치 2)
        return attribute.getDongCode();
    }

    @Override
<<<<<<< HEAD
    public WeatherRegion convertToEntityAttribute(final String dbData) {
=======
    public WeatherRegion convertToEntityAttribute(String dbData) {
>>>>>>> 589cb3d (날씨 정보 조회 배치 2)
        return WeatherRegion.of(dbData);
    }
}