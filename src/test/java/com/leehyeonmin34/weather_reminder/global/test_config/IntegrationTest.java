package com.leehyeonmin34.weather_reminder.global.test_config;

import com.leehyeonmin34.weather_reminder.WeatherReminderApplication;
import com.leehyeonmin34.weather_reminder.global.config.TestProfile;
import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = WeatherReminderApplication.class)
@AutoConfigureMockMvc
@Transactional
@Disabled
@ActiveProfiles(TestProfile.TEST)
public class IntegrationTest {
}
