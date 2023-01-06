package com.leehyeonmin34.weather_reminder.domain.notification.service.dust;

import com.leehyeonmin34.weather_reminder.domain.dust_info.domain.DustInfo;
import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.domain.user.domain.UserBuilder;
import com.leehyeonmin34.weather_reminder.global.common.service.FutureHandler;
import com.leehyeonmin34.weather_reminder.global.test_config.ServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class DustNotiGeneratorTest extends ServiceTest {

    @InjectMocks
    DustNotiGenerator dustNotiGenerator;

    @Mock
    FineDustMessageGenerator fineDustMessageGenerator;

    @Spy
    List<DustMessageGenerator> msgGenerators = new ArrayList<>();

    @Spy
    FutureHandler futureHandler;

    @BeforeEach
    private void init(){
        msgGenerators.add(fineDustMessageGenerator);
    }


    @Test
    public void generateTest(){
        // GIVEN
        when(fineDustMessageGenerator.generate(any(User.class), any(DustInfo.class))).thenReturn("미세먼지 알림");
        User user = UserBuilder.build();

        // WHEN
        String result = dustNotiGenerator.generateMessage(user);

        // THEN
        then(result).isEqualTo("미세먼지 알림");
    }
}
