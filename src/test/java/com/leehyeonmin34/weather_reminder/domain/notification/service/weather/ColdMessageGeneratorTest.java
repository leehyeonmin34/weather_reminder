package com.leehyeonmin34.weather_reminder.domain.notification.service.weather;

import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.domain.user.domain.UserBuilder;
import com.leehyeonmin34.weather_reminder.domain.weather_info.builder.WeatherInfoListBuilder;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherInfoList;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
import com.leehyeonmin34.weather_reminder.global.cache.service.CacheModule;
import com.leehyeonmin34.weather_reminder.global.test_config.ServiceTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;

public class ColdMessageGeneratorTest extends ServiceTest {

    @InjectMocks
    private ColdMessageGenerator coldMessageGenerator;

    @Mock
    private CacheModule cacheModule;

    @ParameterizedTest(name = "{index} : {0}")
    @MethodSource("generateTestConditions")
    public void generateForrealTest(String conditionName, User user, int baseCondition, String expectedResult){

        // GIVEN
        WeatherInfoList weatherInfoList = WeatherInfoListBuilder.build(WeatherRegion.SEOUL, WeatherDataType.TEMP, baseCondition);

        // WHEN
        String result = coldMessageGenerator.generateForReal(user, weatherInfoList);

        // THEN
        System.out.println(result);
        then(result).isEqualTo(expectedResult);

    }

    private static Stream<Arguments> generateTestConditions(){
        String expectedNormal = "🥶 오늘 오전 5시, 6시, 7시, 8시, 9시의 기온이 0.0도보다 낮아요. 옷을 특히 따뜻하게 입고 손난로를 챙겨가세요!\n\n" +
                "두꺼운 옷 한 벌보다 얇은 옷을 여러 겹 입는 게 도움되고, 장갑이나 목도리 등도 도움이 될 수 있어요";
        return Stream.of(
                    Arguments.arguments("알림 조건 충족 - 알림 생성", UserBuilder.buildByOneRegion(), 0, expectedNormal), // 알림이 생성되는 조건
                    Arguments.arguments("알림 조건 불충족 - 알림 미생성", UserBuilder.buildByOneRegion(), 100, "") // 알림이 생성되지 않는 조건 (조건을 만족하는 시간대가 없음)
//                    Arguments.arguments("알림 미설정 - 알림 미생성", UserBuilder.buildUserNoNoti() ,0, "") // 알림이 생성되지 않는 조건 (알림이 설정되어있지 않음)
        );

    }




}
