package com.leehyeonmin34.weather_reminder.domain.weather_info.repository;

import com.leehyeonmin34.weather_reminder.domain.user.model.Region;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.Dong;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class DongConverter implements AttributeConverter<Dong, String> {

    @Override
    public String convertToDatabaseColumn(Dong attribute) {
        return attribute.getDongCode();
    }

    @Override
    public Dong convertToEntityAttribute(String dbData) {
        return Dong.of(dbData);
    }
}