package com.leehyeonmin34.weather_reminder.global.batch.load_info;

import com.leehyeonmin34.weather_reminder.domain.weather_info.domain.WeatherInfo;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherDataTypeConverter;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherRegionConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoadInfoItemSqlParameterSourceProvider implements ItemSqlParameterSourceProvider<WeatherInfo> {

    private final WeatherRegionConverter weatherRegionConverter;

    private final WeatherDataTypeConverter weatherDataTypeConverter;

    @Override
    public SqlParameterSource createSqlParameterSource(final WeatherInfo weatherInfo) {
        return new MapSqlParameterSource()
                .addValue("weatherRegion", weatherRegionConverter.convertToDatabaseColumn(weatherInfo.getWeatherRegion()))
                .addValue("weatherDataType", weatherDataTypeConverter.convertToDatabaseColumn(weatherInfo.getWeatherDataType()))
                .addValue("baseTime", weatherInfo.getBaseTime())
                .addValue("fcstTime", weatherInfo.getFcstTime())
                .addValue("value", weatherInfo.getValue())
                ;
    }
}
