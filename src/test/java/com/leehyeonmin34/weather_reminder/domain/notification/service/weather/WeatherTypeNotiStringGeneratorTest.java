package com.leehyeonmin34.weather_reminder.domain.notification.service.weather;

import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.domain.user.domain.UserBuilder;
import com.leehyeonmin34.weather_reminder.domain.weather_info.builder.WeatherInfoListBuilder;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherInfoList;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
import com.leehyeonmin34.weather_reminder.domain.weather_info.service.WeatherInfoService;
import com.leehyeonmin34.weather_reminder.global.common.service.FutureHandler;
import com.leehyeonmin34.weather_reminder.global.test_config.ServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class WeatherTypeNotiStringGeneratorTest extends ServiceTest {

    @InjectMocks
    WeatherNotiStringGenerator weatherNotiStringGenerator;

    @Mock
    HotNotiStringGenerator hotNotiStringGenerator;

    @Mock
    RainNotiStringGenerator rainNotiStringGenerator;

    @Mock
    ColdNotiStringGenerator coldNotiStringGenerator;

    @Spy
    List<WeatherTypeNotiStringGenerator> weatherTypeNotiStringGenerators = new ArrayList<>();

    @Spy
    FutureHandler futureHandler;

    @Mock
    WeatherInfoService weatherInfoService;

    @BeforeEach
    private void init(){
        weatherTypeNotiStringGenerators.add(hotNotiStringGenerator);
        weatherTypeNotiStringGenerators.add(coldNotiStringGenerator);
        weatherTypeNotiStringGenerators.add(rainNotiStringGenerator);
    }

    @ParameterizedTest(name = "{index} : 날씨 메시지 생성 - {0}")
    @MethodSource("generateMessageTestConditions")
    public void generateMessageTest(String conditionName, boolean hotNotiOn, boolean coldNotiOn, boolean rainNotiOn, User user, String expected){
        // GIVEN
        String hotMsg = hotNotiOn ? "더운 날 알림" : "";
        String coldMsg = coldNotiOn ? "추운 날 알림" : "";
        String rainMsg = rainNotiOn ? "비 알림" : "";
        String todayWeatherURL = "오늘의 날씨 URL";

        when(hotNotiStringGenerator.generate(any(User.class), any(WeatherInfoList.class))).thenReturn(hotMsg);
        when(coldNotiStringGenerator.generate(any(User.class), any(WeatherInfoList.class))).thenReturn(coldMsg);
        when(rainNotiStringGenerator.generate(any(User.class), any(WeatherInfoList.class))).thenReturn(rainMsg);
        when(weatherInfoService.getWeatherInfoListToday(any(WeatherRegion.class))).thenReturn(WeatherInfoListBuilder.build());

        // WHEN
        String result = weatherNotiStringGenerator.generateMessage(user);

        // THEN
        then(result).isEqualTo(expected);
    }

    private static Stream<Arguments> generateMessageTestConditions(){
        return Stream.of(
                Arguments.arguments("1개 지역, 3개 알림", true, true, true, UserBuilder.buildByOneRegion(), "서울 날씨 -----------\n\n더운 날 알림\n\n추운 날 알림\n\n비 알림"),
                Arguments.arguments("1개 지역, 2개 알림", true, true, false, UserBuilder.buildByOneRegion(), "서울 날씨 -----------\n\n더운 날 알림\n\n추운 날 알림"),
                Arguments.arguments("1개 지역, 1개 알림", true, false, false, UserBuilder.buildByOneRegion(), "서울 날씨 -----------\n\n더운 날 알림"),
                Arguments.arguments("1개 지역, 0개 알림", false, false, false, UserBuilder.buildByOneRegion(), ""),
                Arguments.arguments("2개 지역, 3개 알림", true, true, true, UserBuilder.buildByTwoRegion(), "서울 날씨 -----------\n\n더운 날 알림\n\n추운 날 알림\n\n비 알림\n\n부산 날씨 -----------\n\n더운 날 알림\n\n추운 날 알림\n\n비 알림")
        );
    }

    @Test
    @DisplayName("날씨 메시지 생성 - 추운 날 알림은 예외로 생성 못함")
    public void generateMessageWithExceptionTest(){
        // GIVEN
        when(hotNotiStringGenerator.generate(any(User.class), any(WeatherInfoList.class))).thenReturn("더운 날 알림");
        when(coldNotiStringGenerator.generate(any(User.class), any(WeatherInfoList.class))).thenThrow(new RuntimeException());
        when(rainNotiStringGenerator.generate(any(User.class), any(WeatherInfoList.class))).thenReturn("비 알림");
        when(weatherInfoService.getWeatherInfoListToday(any(WeatherRegion.class))).thenReturn(WeatherInfoListBuilder.build());

        // WHEN
        String result = weatherNotiStringGenerator.generateMessage(UserBuilder.buildByOneRegion());

        // THEN
        then(result).isEqualTo("서울 날씨 -----------\n\n더운 날 알림\n\n비 알림");
    }

}
