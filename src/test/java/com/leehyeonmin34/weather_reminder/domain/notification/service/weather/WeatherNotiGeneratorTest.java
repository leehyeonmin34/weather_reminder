package com.leehyeonmin34.weather_reminder.domain.notification.service.weather;

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
import java.util.concurrent.Future;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class WeatherNotiGeneratorTest extends ServiceTest {

    @InjectMocks
    WeatherNotiGenerator weatherNotiGenerator;

    @Mock
    HotMessageGenerator hotMessageGenerator;

    @Mock
    RainMessageGenerator rainMessageGenerator;

    @Mock
    ColdMessageGenerator coldMessageGenerator;

    @Spy
    List<WeatherMessageGenerator> msgGenerators = new ArrayList<>();

    @Spy
    FutureHandler futureHandler;

    @BeforeEach
    private void init(){
        msgGenerators.add(hotMessageGenerator);
        msgGenerators.add(coldMessageGenerator);
        msgGenerators.add(rainMessageGenerator);
    }


    @Test
    public void generateTest(){
        // GIVEN
        when(hotMessageGenerator.generate(any(User.class), any(WeatherInfo.class))).thenReturn("더운 날 알림");
        when(coldMessageGenerator.generate(any(User.class), any(WeatherInfo.class))).thenReturn("추운 날 알림");
        when(rainMessageGenerator.generate(any(User.class), any(WeatherInfo.class))).thenReturn("비 온 날 알림");
        User user = UserBuilder.build();

        // WHEN
        String result = weatherNotiGenerator.generateMessage(user);

        // THEN
        then(result).isEqualTo("더운 날 알림\n\n추운 날 알림\n\n비 온 날 알림");
    }

}
