package com.leehyeonmin34.weather_reminder.global.batch;

import com.leehyeonmin34.weather_reminder.domain.notification.service.common.NotiGeneratorAndEnqueuer;
import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.domain.user.domain.UserBuilder;
import com.leehyeonmin34.weather_reminder.domain.user.repository.UserRepository;
import com.leehyeonmin34.weather_reminder.global.test_config.BatchTestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;

import static org.assertj.core.api.BDDAssertions.then;


@SpringBatchTest
@SpringBootTest
//@SpringBootTest(classes = {BatchTestConfig.class, SendNotiBatch.class})
@Import({BatchTestConfig.class})
@Rollback(false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SendNotiBatchTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @MockBean
    private NotiGeneratorAndEnqueuer notiGeneratorAndEnqueuer;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void init(){
        user = userRepository.save(UserBuilder.build());
    }

    @AfterEach
    public void rollback(){
        userRepository.delete(user);
    }

    @Test
    @DisplayName("알림 전송 배치 테스트")
    public void sendNotiBatchTest() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        then(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
    }

}
