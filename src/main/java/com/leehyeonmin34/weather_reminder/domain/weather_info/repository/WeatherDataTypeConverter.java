package com.leehyeonmin34.weather_reminder.domain.weather_info.repository;


import com.leehyeonmin34.weather_reminder.domain.user.model.Region;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
@Component
public class WeatherDataTypeConverter implements AttributeConverter<WeatherDataType, String> {


    @Override
    public String convertToDatabaseColumn(final WeatherDataType attribute) {
        return attribute.getCode();
    }

    @Override
    public WeatherDataType convertToEntityAttribute(final String dbData) {
        return WeatherDataType.of(dbData);
    }
}
