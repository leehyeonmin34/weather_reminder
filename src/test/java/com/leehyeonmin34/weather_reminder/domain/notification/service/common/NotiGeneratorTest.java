package com.leehyeonmin34.weather_reminder.domain.notification.service.common;

import com.leehyeonmin34.weather_reminder.domain.notification.domain.Notification;
import com.leehyeonmin34.weather_reminder.domain.notification.service.dust.DustNotiStringGenerator;
import com.leehyeonmin34.weather_reminder.domain.notification.service.today_weather.TodayWeatherURLNotiStringGenerator;
import com.leehyeonmin34.weather_reminder.domain.notification.service.weather.*;
import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.domain.user.domain.UserBuilder;
import com.leehyeonmin34.weather_reminder.global.common.service.FutureHandler;
import com.leehyeonmin34.weather_reminder.global.test_config.ServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

public class NotiGeneratorTest extends ServiceTest {

    @InjectMocks
    NotiGenerator notiGenerator;

    @Mock
    WeatherNotiStringGenerator weatherNotiStringGenerator;

    @Mock
    DustNotiStringGenerator dustNotiStringGenerator;

    @Mock
    TodayWeatherURLNotiStringGenerator todayWeatherURLNotiStringGenerator;

    @Spy
    List<NotiStringGenerator> notiStringGenerators = new ArrayList<>();

    @Spy
    FutureHandler futureHandler;

    @BeforeEach
    private void init(){
        notiStringGenerators.add(weatherNotiStringGenerator);
        notiStringGenerators.add(dustNotiStringGenerator);
    }


    @MethodSource("generateTestConditions")
    @ParameterizedTest(name = "{index} : {0}")
    public void generateTest(String conditionName, boolean weatherNotiExists, boolean dustNotiExists, String expectedMsg){

        final String weatherMsg = weatherNotiExists ? "날씨 알림" : "";
        final String dustMsg = dustNotiExists ? "먼지 알림" : "";
        final String todayWeatherURLMsg = "\n\n오늘 날씨 URL";

        // GIVEN
        lenient().when(weatherNotiStringGenerator.generateMessage(any(User.class))).thenReturn(weatherMsg);
        lenient().when(dustNotiStringGenerator.generateMessage(any(User.class))).thenReturn(dustMsg);
        lenient().when(todayWeatherURLNotiStringGenerator.generateMessage(any(User.class))).thenReturn(todayWeatherURLMsg);
        User user = UserBuilder.buildByOneRegion();

        // WHEN
        Optional<Notification> result = notiGenerator.generate(user);

        // THEN
        if(!weatherNotiExists && !dustNotiExists)
            then(result.isEmpty()).isTrue();
        else
            then(result.orElseThrow(NullPointerException::new).getMsg()).isEqualTo(expectedMsg);
    }


    private static Stream<Arguments> generateTestConditions() {
        return Stream.of(
                Arguments.arguments("날씨 알림, 먼지 알림", true, true, "날씨 알림\n\n먼지 알림\n\n오늘 날씨 URL"),
                Arguments.arguments("날씨 알림", true, false, "날씨 알림\n\n오늘 날씨 URL"),
                Arguments.arguments("먼지 알림", false, true, "먼지 알림\n\n오늘 날씨 URL"),
                Arguments.arguments("알림 없음", false, false, "")
        );
    }

}
