package com.leehyeonmin34.weather_reminder.domain.user.repository;

import com.leehyeonmin34.weather_reminder.domain.user.model.Region;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class RegionListConverter implements AttributeConverter<List<Region>, String> {

    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(final List<Region> attribute) {
        return attribute.stream().map(item -> item.getCode()).collect(Collectors.joining(SPLIT_CHAR));
    }

    @Override
    public List<Region> convertToEntityAttribute(final String dbData) {
        return Arrays.stream(dbData.split(SPLIT_CHAR))
                .map(Region::of)
                .collect(Collectors.toList());

    }
}