package com.leehyeonmin34.weather_reminder.global;

import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDateTime;

@Converter
@Component
public class TimeConverter implements AttributeConverter<LocalDateTime, Integer> {


    @Override
    public Integer convertToDatabaseColumn(LocalDateTime attribute) {
        return attribute.getSecond();
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Integer dbData) {
        return LocalDateTime.now();
    }
}