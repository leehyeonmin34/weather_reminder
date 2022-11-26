package com.leehyeonmin34.weather_reminder.global.parent;

import com.leehyeonmin34.weather_reminder.WeatherReminderApplication;
import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = WeatherReminderApplication.class)
@AutoConfigureMockMvc
@Transactional
@Disabled
public class IntegrationTest {
}
