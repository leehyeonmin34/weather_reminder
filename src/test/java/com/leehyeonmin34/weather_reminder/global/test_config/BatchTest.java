package com.leehyeonmin34.weather_reminder.global.test_config;

import com.leehyeonmin34.weather_reminder.global.config.TestProfile;
import org.junit.Ignore;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@Ignore
@ExtendWith(SpringExtension.class)
@SpringBatchTest
@ActiveProfiles(TestProfile.TEST)
// 테스트 대상 클래스에 아래 어노테이션 붙여야함
// @SpringBootTest(classes = {BatchTestConfig.class, 테스트_대상_배치_클래스.class})
public class BatchTest {

}