package com.leehyeonmin34.weather_reminder.domain.weather_info.repository;

import com.leehyeonmin34.weather_reminder.domain.weather_info.service.WeatherApiTimeStringConverter;
import com.leehyeonmin34.weather_reminder.domain.weather_info.service.WeatherApiTimeTimestampConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Converter
@Component
@RequiredArgsConstructor
public class WeatherApiTimeConverterForDB implements AttributeConverter<LocalDateTime, Timestamp> {


    @Override
    public Timestamp convertToDatabaseColumn(final LocalDateTime attribute) {
        return WeatherApiTimeTimestampConverter.serialize(attribute);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(final Timestamp dbData) {
        return WeatherApiTimeTimestampConverter.parse(dbData);
    }
}