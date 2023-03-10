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
        String expectedNormal = "đĨļ ė¤ë ė¤ė  5ė, 6ė, 7ė, 8ė, 9ėė ę¸°ė¨ė´ 0.0ëëŗ´ë¤ ëŽėė. ėˇė íší ë°ëģíę˛ ėęŗ  ėëëĄëĨŧ ėąę˛¨ę°ė¸ė!\n\n" +
                "ëęēŧė´ ėˇ í ë˛ëŗ´ë¤ ėė ėˇė ėŦëŦ ę˛š ėë ę˛ ëėëęŗ , ėĨę°ė´ë ëĒŠëëĻŦ ëąë ëėė´ ë  ė ėė´ė";
        return Stream.of(
                    Arguments.arguments("ėëĻŧ ėĄ°ęą´ ėļŠėĄą - ėëĻŧ ėėą", UserBuilder.buildByOneRegion(), 0, expectedNormal), // ėëĻŧė´ ėėąëë ėĄ°ęą´
                    Arguments.arguments("ėëĻŧ ėĄ°ęą´ ëļėļŠėĄą - ėëĻŧ ë¯¸ėėą", UserBuilder.buildByOneRegion(), 100, "") // ėëĻŧė´ ėėąëė§ ėë ėĄ°ęą´ (ėĄ°ęą´ė ë§ėĄąíë ėę°ëę° ėė)
//                    Arguments.arguments("ėëĻŧ ë¯¸ė¤ė  - ėëĻŧ ë¯¸ėėą", UserBuilder.buildUserNoNoti() ,0, "") // ėëĻŧė´ ėėąëė§ ėë ėĄ°ęą´ (ėëĻŧė´ ė¤ė ëė´ėė§ ėė)
        );

    }




}
