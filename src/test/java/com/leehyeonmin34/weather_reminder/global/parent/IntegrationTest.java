package com.leehyeonmin34.weather_reminder.global.parent;

import com.leehyeonmin34.auth_practice.AuthPracticeApplication;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = AuthPracticeApplication.class)
@AutoConfigureMockMvc
@Transactional
@Ignore
public class IntegrationTest {
}
