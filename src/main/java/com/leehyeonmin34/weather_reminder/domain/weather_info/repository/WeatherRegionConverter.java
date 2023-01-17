package com.leehyeonmin34.weather_reminder.domain.weather_info.repository;

import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
@Component
public class WeatherRegionConverter implements AttributeConverter<WeatherRegion, String> {
    @Override
    public String convertToDatabaseColumn(WeatherRegion attribute) {
        return attribute.getDongCode();
    }

    @Override
    public WeatherRegion convertToEntityAttribute(String dbData) {
        return WeatherRegion.of(dbData);
    }
}