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

public class HotMessageGeneratorTest extends ServiceTest {

    @InjectMocks
    private HotMessageGenerator hotMessageGenerator;

    @Mock
    private CacheModule cacheModule;

    @ParameterizedTest(name = "{index} : {0}")
    @MethodSource("generateTestConditions")
    public void generateTest(String conditionName, User user, int baseCondition, String expectedResult){

        // GIVEN
        WeatherInfoList weatherInfoList = WeatherInfoListBuilder.build(WeatherRegion.SEOUL, WeatherDataType.TEMP, baseCondition);

        // WHEN
        String result = hotMessageGenerator.generateForReal(user, weatherInfoList);

        // THEN
        System.out.println(result);
        then(result).isEqualTo(expectedResult);

    }

    private static Stream<Arguments> generateTestConditions() {
        String expectedNormal = "\uD83E\uDD75 오늘 오후 2시, 3시, 4시, 5시, 6시, 7시, 8시의 기온이 3.0도보다 높아요.\n" +
                "\n" +
                "옷을 시원하게 입고 썬크림, 손풍기 등에 신경써요 !";
        return Stream.of(
                Arguments.arguments("알림 조건 충족 - 알림 생성", UserBuilder.buildByOneRegion(), 0, expectedNormal), // 알림이 생성되는 조건
                Arguments.arguments("알림 조건 불충족 - 알림 미생성", UserBuilder.buildByOneRegion(), -100, "") // 알림이 생성되지 않는 조건 (조건을 만족하는 시간대가 없음)
        );
    }
}
