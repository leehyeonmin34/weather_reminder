package com.leehyeonmin34.weather_reminder.domain.weather_info.repository;

import com.leehyeonmin34.weather_reminder.domain.weather_info.builder.WeatherInfoListBuilder;
import com.leehyeonmin34.weather_reminder.domain.weather_info.domain.WeatherInfo;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
import com.leehyeonmin34.weather_reminder.domain.weather_info.service.WeatherApiTimeConverter;
import com.leehyeonmin34.weather_reminder.global.test_config.RepositoryTestWithoutTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;

@Slf4j
public class WeatherInfoRepositoryTest extends RepositoryTestWithoutTransaction {

    @Autowired
    private WeatherInfoRepository weatherInfoRepository;

    private List<WeatherInfo> weatherInfoList;

    private final static int weatherInfoListSize = WeatherInfoListBuilder.build(LocalDateTime.now().minusDays(1), WeatherRegion.SEOUL, WeatherDataType.TEMP, 0).getTempWeatherInfoList().size();

    @BeforeEach
    public void init(){

        // 다양한 날짜의 서울 '기온' 정보
        List<WeatherInfo> list_1 = WeatherInfoListBuilder.build(LocalDateTime.now().minusDays(1), WeatherRegion.SEOUL, WeatherDataType.TEMP, 0).getTempWeatherInfoList();
        List<WeatherInfo> list_2 = WeatherInfoListBuilder.build(LocalDateTime.now(), WeatherRegion.SEOUL, WeatherDataType.TEMP, 0).getTempWeatherInfoList();
        List<WeatherInfo> list_3 = WeatherInfoListBuilder.build(LocalDateTime.now().plusDays(1), WeatherRegion.SEOUL, WeatherDataType.TEMP, 0).getTempWeatherInfoList();

        // 서울의 '비' 정보
        List<WeatherInfo> list_4 = WeatherInfoListBuilder.build(LocalDateTime.now(), WeatherRegion.SEOUL, WeatherDataType.RAIN, 0).getRainWeatherInfoList();

        // 부산의 '기온' 정보
        List<WeatherInfo> list_5 = WeatherInfoListBuilder.build(LocalDateTime.now(), WeatherRegion.BUSAN, WeatherDataType.TEMP, 0).getTempWeatherInfoList();

        List<WeatherInfo> toSave = new ArrayList<>();
        toSave.addAll(list_1);
        toSave.addAll(list_2);
        toSave.addAll(list_3);
        toSave.addAll(list_4);
        toSave.addAll(list_5);
        weatherInfoList = weatherInfoRepository.saveAll(toSave);
    }

    @AfterEach
    public void rollback(){
        weatherInfoRepository.deleteAllInBatch(weatherInfoList);
    }

    @ParameterizedTest(name = "{index} : 특정 지역의 오늘 날씨 정보 조회 - {0}")
    @MethodSource("findAllTodayByWeatherRegionTestConditoins")
    @DisplayName("특정 지역의 오늘 날씨 정보 조회")
    public void findAllTodayByWeatherRegionTest(String regionName, WeatherRegion weatherRegion, int expectedResultCount){

        // WHEN
        List<WeatherInfo> result = weatherInfoRepository.findAllTodayByWeatherRegion(weatherRegion);

        // THEN - 오늘의 정보만 조회되는지 확인
        then(result.size()).isEqualTo(expectedResultCount);
        result.forEach(item -> isToday(item.getFcstTime()));
    }

    private void isToday(LocalDateTime time){
        final String timeDay = WeatherApiTimeConverter.serialize(time).substring(0, 8);
        final String today = WeatherApiTimeConverter.serialize(LocalDateTime.now()).substring(0,8);

        then(timeDay).isEqualTo(today);
    }

    private static Stream<Arguments> findAllTodayByWeatherRegionTestConditoins(){
        return Stream.of(
                Arguments.arguments("서울", WeatherRegion.SEOUL, weatherInfoListSize * 2),
                Arguments.arguments("부산", WeatherRegion.BUSAN, weatherInfoListSize * 1)
        );
    }

}
