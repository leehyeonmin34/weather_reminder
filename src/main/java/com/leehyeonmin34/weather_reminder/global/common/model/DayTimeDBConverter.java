package com.leehyeonmin34.weather_reminder.global.common.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DayTimeDBConverter implements AttributeConverter<DayTime, String> {

    @Override
    public String convertToDatabaseColumn(final DayTime attribute) {
        return DayTimeConverter.serialize(attribute);
    }

    @Override
    public DayTime convertToEntityAttribute(String dbData) {
        return DayTimeConverter.deserialize(dbData);
    }

}
