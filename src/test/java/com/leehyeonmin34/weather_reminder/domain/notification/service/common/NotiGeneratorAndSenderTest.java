package com.leehyeonmin34.weather_reminder.domain.notification.service.common;

import com.leehyeonmin34.weather_reminder.domain.notification.service.dust.DustNotiGenerator;
import com.leehyeonmin34.weather_reminder.domain.notification.service.weather.*;
import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.domain.user.domain.UserBuilder;
import com.leehyeonmin34.weather_reminder.domain.weather_info.domain.WeatherInfo;
import com.leehyeonmin34.weather_reminder.global.common.service.FutureHandler;
import com.leehyeonmin34.weather_reminder.global.parent.ServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class NotiGeneratorAndSenderTest extends ServiceTest {

    @InjectMocks
    NotiGeneratorAndSender notiGeneratorAndSender;

    @Mock
    WeatherNotiGenerator weatherNotiGenerator;

    @Mock
    DustNotiGenerator dustNotiGenerator;

    @Spy
    List<NotiGenerator> notiGenerators = new ArrayList<>();

    @Spy
    FutureHandler futureHandler;

    @Mock
    NotiQ notiQ;

    @BeforeEach
    private void init(){
        notiGenerators.add(weatherNotiGenerator);
        notiGenerators.add(dustNotiGenerator);
    }


    @Test
    public void generateTest(){
        // GIVEN
        when(weatherNotiGenerator.generateMessage(any(User.class))).thenReturn("날씨 알림");
        when(dustNotiGenerator.generateMessage(any(User.class))).thenReturn("먼지 알림");
        User user = UserBuilder.build();

        // WHEN - THEN
        assertThatCode(()-> notiGeneratorAndSender.generateNotiAndSend(user))
                .doesNotThrowAnyException();

    }

}
