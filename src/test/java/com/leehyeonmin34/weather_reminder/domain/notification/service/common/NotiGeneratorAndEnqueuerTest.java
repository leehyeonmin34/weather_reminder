package com.leehyeonmin34.weather_reminder.domain.notification.service.common;

import com.leehyeonmin34.weather_reminder.domain.notification.domain.Notification;
import com.leehyeonmin34.weather_reminder.domain.notification.service.dust.DustNotiGenerator;
import com.leehyeonmin34.weather_reminder.domain.notification.service.today_weather.TodayWeatherURLMessageGenerator;
import com.leehyeonmin34.weather_reminder.domain.notification.service.weather.*;
import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.domain.user.domain.UserBuilder;
import com.leehyeonmin34.weather_reminder.global.common.service.FutureHandler;
import com.leehyeonmin34.weather_reminder.global.message_q.service.MessageFactory;
import com.leehyeonmin34.weather_reminder.global.message_q.service.MessageQ;
import com.leehyeonmin34.weather_reminder.global.test_config.ServiceTest;
import org.junit.jupiter.api.BeforeEach;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

public class NotiGeneratorAndEnqueuerTest extends ServiceTest {

    @InjectMocks
    NotiGeneratorAndEnqueuer notiGeneratorAndEnqueuer;

    @Mock
    WeatherNotiGenerator weatherNotiGenerator;

    @Mock
    DustNotiGenerator dustNotiGenerator;

    @Mock
    TodayWeatherURLMessageGenerator todayWeatherURLMessageGenerator;

    @Spy
    List<NotiGenerator> notiGenerators = new ArrayList<>();

    @Spy
    FutureHandler futureHandler;

    @Mock
    MessageQ messageQ;

    @Mock
    MessageFactory messageFactory;

    @BeforeEach
    private void init(){
        notiGenerators.add(weatherNotiGenerator);
        notiGenerators.add(dustNotiGenerator);
    }


    @MethodSource("generateTestConditions")
    @ParameterizedTest(name = "{index} : {0}")
    public void generateTest(String conditionName, boolean weatherNotiExists, boolean dustNotiExists, String expectedMsg){

        final String weatherMsg = weatherNotiExists ? "?????? ??????" : "";
        final String dustMsg = dustNotiExists ? "?????? ??????" : "";
        final String todayWeatherURLMsg = "\n\n?????? ?????? URL";

        // GIVEN
        lenient().when(weatherNotiGenerator.generateMessage(any(User.class))).thenReturn(weatherMsg);
        lenient().when(dustNotiGenerator.generateMessage(any(User.class))).thenReturn(dustMsg);
        lenient().when(todayWeatherURLMessageGenerator.generateMessage(any(User.class))).thenReturn(todayWeatherURLMsg);
        User user = UserBuilder.buildByOneRegion();

        // WHEN
        Notification result = notiGeneratorAndEnqueuer.generateNotiAndEnqueue(user);

        // THEN
        if(!weatherNotiExists && !dustNotiExists)
            then(result).isNull();
        else
            then(result.getMsg()).isEqualTo(expectedMsg);
    }


    private static Stream<Arguments> generateTestConditions() {
        return Stream.of(
                Arguments.arguments("?????? ??????, ?????? ??????", true, true, "?????? ??????\n\n?????? ??????\n\n?????? ?????? URL"),
                Arguments.arguments("?????? ??????", true, false, "?????? ??????\n\n?????? ?????? URL"),
                Arguments.arguments("?????? ??????", false, true, "?????? ??????\n\n?????? ?????? URL"),
                Arguments.arguments("?????? ??????", false, false, "")
        );
    }

}
