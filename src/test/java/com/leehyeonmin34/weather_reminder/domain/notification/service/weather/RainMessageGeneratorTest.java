package com.leehyeonmin34.weather_reminder.domain.notification.service.weather;

import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.domain.user.domain.UserBuilder;
import com.leehyeonmin34.weather_reminder.domain.weather_info.builder.WeatherInfoListBuilder;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherInfoList;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
import com.leehyeonmin34.weather_reminder.global.cache.service.CacheModule;
import com.leehyeonmin34.weather_reminder.global.test_config.ServiceTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;

public class RainMessageGeneratorTest extends ServiceTest {

    @InjectMocks
    private RainMessageGenerator rainMessageGenerator;

    @Mock
    private CacheModule cacheModule;

    @ParameterizedTest(name = "{index} : {0}")
    @MethodSource("generateTestConditions")
    public void generateTest(String conditionName, User user, int baseCondition, String expectedResult){

        // GIVEN
        WeatherInfoList weatherInfoList = WeatherInfoListBuilder.build(WeatherRegion.SEOUL, WeatherDataType.RAIN, baseCondition);

        // WHEN
        String result = rainMessageGenerator.generateForReal(user, weatherInfoList);

        // THEN
        System.out.println(result);
        then(result).isEqualTo(expectedResult);

    }

    private static Stream<Arguments> generateTestConditions() {
        String normalRainMsg = "\uD83C\uDF27️ 오늘 오후 1시, 2시, 3시, 4시, 5시, 6시, 7시, 8시, 9시에 최대 7.000000mm의 비가 예보되었어요. 우산을 잊지마세요! 흰 옷은 피하고 장화나 샌들을 신으면 더 좋겠죠?";
        String strongRainMsg = "\uD83C\uDF27️ 오늘 오전 5시, 6시, 7시, 8시, 9시, 10시, 11시, 오후 12시, 1시, 2시, 3시, 4시, 5시, 6시, 7시, 8시, 9시, 10시, 11시에 최대 27.000000mm의 매우 강한 비가 예보되었어요. 우산을 잊지마세요! 흰 옷은 피하고 장화나 샌들을 신으면 더 좋겠죠?";
        return Stream.of(
                Arguments.arguments("보통 비 예정 - 알림 생성", UserBuilder.buildByOneRegion(), 0, normalRainMsg), // 알림이 생성되는 조건
                Arguments.arguments("강한 비 예정 - 알림 생성", UserBuilder.buildByOneRegion(), 20, strongRainMsg), // 알림이 생성되는 조건
                Arguments.arguments("알림 조건 불충족 - 알림 미생성", UserBuilder.buildByOneRegion(), -100, "") // 알림이 생성되지 않는 조건 (조건을 만족하는 시간대가 없음)
        );
    }
}
