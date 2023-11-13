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

public class DustNotiStringGeneratorTest extends ServiceTest {

    @InjectMocks
    DustNotiStringGenerator dustNotiStringGenerator;

    @Mock
    FineDustNotiStringGenerator fineDustNotiStringGenerator;

    @Spy
    List<DustTypeNotiStringGenerator> notiStringGenerators = new ArrayList<>();

    @Spy
    FutureHandler futureHandler;

    @BeforeEach
    private void init(){
        notiStringGenerators.add(fineDustNotiStringGenerator);
    }


    @Test
    public void generateTest(){
        // GIVEN
        when(fineDustNotiStringGenerator.generate(any(User.class), any(DustInfo.class))).thenReturn("미세먼지 알림");
        User user = UserBuilder.buildByOneRegion();

        // WHEN
        String result = dustNotiStringGenerator.generateMessage(user);

        // THEN
        then(result).isEqualTo("미세먼지 알림");
    }
}
