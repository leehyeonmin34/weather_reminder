package com.leehyeonmin34.weather_reminder.domain.weather_info.builder;

import com.leehyeonmin34.weather_reminder.domain.weather_info.domain.WeatherInfo;
import com.leehyeonmin34.weather_reminder.domain.weather_info.dto.WeatherApiResponseDto;
import com.leehyeonmin34.weather_reminder.domain.weather_info.dto.WeatherApiTestResponseProvider;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherInfoList;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
import com.leehyeonmin34.weather_reminder.domain.weather_info.service.WeatherApiTimeConverter;
import lombok.RequiredArgsConstructor;
import org.junit.Ignore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Ignore
public class WeatherInfoListBuilder {

    public static WeatherInfoList build(){
        return build(WeatherRegion.SEOUL, WeatherDataType.TEMP, 0);
    }

    public static WeatherInfoList build(WeatherRegion region, WeatherDataType dataType, float baseCondition){
        LocalDateTime baseDate = LocalDateTime.of(2023,1,1,1,1);
        return build(baseDate, region, dataType, baseCondition);

    }

    public static WeatherInfoList build(LocalDateTime baseDate, WeatherRegion region, WeatherDataType dataType, float baseCondition){
        List<WeatherInfo> temp = new ArrayList<>();

        String baseDateString = WeatherApiTimeConverter.serialize(baseDate).substring(0, 8);
        String reportedDate = WeatherApiTimeConverter.serialize(baseDate.minusDays(1)).substring(0, 8);

        temp.add(new WeatherInfo(reportedDate + "2100", baseDateString + "0500", region, dataType, baseCondition - 5));
        temp.add(new WeatherInfo(reportedDate + "2100", baseDateString + "0600", region, dataType, baseCondition - 4));
        temp.add(new WeatherInfo(reportedDate + "2100", baseDateString + "0700", region, dataType, baseCondition - 3));
        temp.add(new WeatherInfo(reportedDate + "2100", baseDateString + "0800", region, dataType, baseCondition - 2));
        temp.add(new WeatherInfo(reportedDate + "2100", baseDateString + "0900", region, dataType, baseCondition - 1));
        temp.add(new WeatherInfo(reportedDate + "2100", baseDateString + "1000", region, dataType, baseCondition + 0));
        temp.add(new WeatherInfo(reportedDate + "2100", baseDateString + "1100", region, dataType, baseCondition + 1));
        temp.add(new WeatherInfo(reportedDate + "2100", baseDateString + "1200", region, dataType, baseCondition + 2));
        temp.add(new WeatherInfo(reportedDate + "2100", baseDateString + "1300", region, dataType, baseCondition + 3));
        temp.add(new WeatherInfo(reportedDate + "2100", baseDateString + "1400", region, dataType, baseCondition + 4));
        temp.add(new WeatherInfo(reportedDate + "2100", baseDateString + "1500", region, dataType, baseCondition + 5));
        temp.add(new WeatherInfo(reportedDate + "2100", baseDateString + "1600", region, dataType, baseCondition + 6));
        temp.add(new WeatherInfo(reportedDate + "2100", baseDateString + "1700", region, dataType, baseCondition + 7));
        temp.add(new WeatherInfo(reportedDate + "2100", baseDateString + "1800", region, dataType, baseCondition + 6));
        temp.add(new WeatherInfo(reportedDate + "2100", baseDateString + "1900", region, dataType, baseCondition + 5));
        temp.add(new WeatherInfo(reportedDate + "2100", baseDateString + "2000", region, dataType, baseCondition + 4));
        temp.add(new WeatherInfo(reportedDate + "2100", baseDateString + "2100", region, dataType, baseCondition + 3));
        temp.add(new WeatherInfo(reportedDate + "2100", baseDateString + "2200", region, dataType, baseCondition + 2));
        temp.add(new WeatherInfo(reportedDate + "2100", baseDateString + "2300", region, dataType, baseCondition + 1));

        return new WeatherInfoList(region, temp);
    }





}
