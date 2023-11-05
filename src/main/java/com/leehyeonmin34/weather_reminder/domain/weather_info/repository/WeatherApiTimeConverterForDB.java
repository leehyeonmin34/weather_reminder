package com.leehyeonmin34.weather_reminder.domain.weather_info.repository;

import com.leehyeonmin34.weather_reminder.domain.weather_info.service.WeatherApiTimeStringConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDateTime;

@Converter
@Component
@RequiredArgsConstructor
public class WeatherApiTimeConverterForDB implements AttributeConverter<LocalDateTime, String> {


    @Override
    public String convertToDatabaseColumn(final LocalDateTime attribute) {
        return WeatherApiTimeStringConverter.serialize(attribute);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(final String dbData) {
        return WeatherApiTimeStringConverter.parse(dbData);
    }
}