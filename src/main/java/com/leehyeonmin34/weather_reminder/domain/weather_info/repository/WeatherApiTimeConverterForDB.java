package com.leehyeonmin34.weather_reminder.domain.weather_info.repository;

import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType;
import com.leehyeonmin34.weather_reminder.domain.weather_info.service.WeatherApiTimeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDateTime;

@Converter
public class WeatherApiTimeConverterForDB implements AttributeConverter<LocalDateTime, String> {


    @Override
    public String convertToDatabaseColumn(LocalDateTime attribute) {
        return WeatherApiTimeConverter.serialize(attribute);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(String dbData) {
        return WeatherApiTimeConverter.parse(dbData);
    }
}