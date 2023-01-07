package com.leehyeonmin34.weather_reminder.global;

import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherDataTypeConverter;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherRegionConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDomainItemSqlParameterSourceProvider implements ItemSqlParameterSourceProvider<TestDomain> {

    private final TimeConverter timeConverter;


    @Override
    public SqlParameterSource createSqlParameterSource(final TestDomain testDomain) {
        return new MapSqlParameterSource()
                .addValue("name", testDomain.getName())
                .addValue("time", timeConverter.convertToDatabaseColumn(testDomain.getTime()))
                .addValue("column2", testDomain.getColumn2())
                .addValue("column3", testDomain.getColumn3())
                .addValue("column4", testDomain.getColumn4())
                .addValue("column5", testDomain.getColumn5())
                .addValue("column6", testDomain.getColumn6())
                .addValue("column7", testDomain.getColumn7())
                ;
    }
}

