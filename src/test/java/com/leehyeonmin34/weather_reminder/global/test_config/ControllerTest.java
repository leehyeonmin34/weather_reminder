package com.leehyeonmin34.weather_reminder.global.test_config;

import com.leehyeonmin34.weather_reminder.global.config.TestProfile;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@Disabled
@ExtendWith(MockitoExtension.class)
@ActiveProfiles(TestProfile.TEST)
public class ControllerTest {

}
