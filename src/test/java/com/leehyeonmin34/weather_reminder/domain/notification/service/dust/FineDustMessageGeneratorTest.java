package com.leehyeonmin34.weather_reminder.domain.notification.service.dust;

import com.leehyeonmin34.weather_reminder.domain.dust_info.domain.DustInfo;
import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.domain.user.domain.UserBuilder;
import com.leehyeonmin34.weather_reminder.global.test_config.ServiceTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.assertj.core.api.BDDAssertions.then;

public class FineDustMessageGeneratorTest extends ServiceTest {

    @InjectMocks
    private FineDustMessageGenerator fineDustMessageGenerator;

    @Test
    public void generateTest(){

        // GIVEN
        DustInfo dustInfo = new DustInfo();
        User user = UserBuilder.buildByOneRegion();

        // WHEN
        String result = fineDustMessageGenerator.generate(user, dustInfo);

        // THEN
        then(result).isEqualTo("미세먼지 메시지");

    }

}
