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

        int[] deltaNums = {-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 6, 5, 4, 3, 2, 1};
        int deltaNumsLength = deltaNums.length;
        String reportDateTime = reportedDate + "2100";
        int startTime = 5;

        for(int i = 0; i < deltaNumsLength; i++)
            temp.add(new WeatherInfo(reportDateTime, baseDateString + convertToString(startTime + i) + "00", region, dataType, baseCondition + deltaNums[i]));

        return new WeatherInfoList(region, temp);
    }

    private static String convertToString(int time){
        return time < 10 ? "0" + String.valueOf(time) : String.valueOf(time);
    }




}
