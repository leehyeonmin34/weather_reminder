package com.leehyeonmin34.weather_reminder.global.batch.send_noti;

import com.leehyeonmin34.weather_reminder.domain.notification.service.common.NotiGeneratorAndEnqueuer;
import com.leehyeonmin34.weather_reminder.domain.user.domain.QUser;
import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.domain.user.domain.UserBuilder;
import com.leehyeonmin34.weather_reminder.domain.user.repository.UserRepository;
import com.leehyeonmin34.weather_reminder.global.common.model.DayTime;
import com.leehyeonmin34.weather_reminder.global.test_config.BatchTestConfig;
import com.leehyeonmin34.weather_reminder.global.test_config.BatchTestSupport;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Ignore
public class SendNotiBatchTest extends BatchTestSupport {

    @Autowired
    @Qualifier("sendNotiJob")
    private Job sendNotiJob;

    @Override
    protected Job getJob() {
        return sendNotiJob;
    }

    @MockBean
    private NotiGeneratorAndEnqueuer notiGeneratorAndEnqueuer;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JPAQueryFactory query;

    @Autowired
    private SendNotiRollback rollback;


    @Test
    @DisplayName("알림 전송 배치 테스트 - 3명 중 2명만")
    public void sendNotiBatchTest() throws Exception {

        // GIVEN
        User user1 = UserBuilder.buildByOneRegion();
        User user2 = UserBuilder.buildByOneRegion();
        User user3 = UserBuilder.buildByOneRegion();

        user1.setNotiTime(new DayTime(9,0));
        user2.setNotiTime(new DayTime(9,0));
        user3.setNotiTime(new DayTime(9,10));

        List<User> savedUsers = saveAll(List.of(user1, user2, user3));

        JobParameters jobParameters = new JobParametersBuilder(jobExplorer) // jobExplorer - 메타 테이블에 대한 read only 쿼리 기능을 위한 인터페이스, runId 조회 가능
                .getNextJobParameters(sendNotiJob)
                .addString("notiTimeString", "0900")
                .toJobParameters();

        // WHEN
        launchJob(sendNotiJob, jobParameters);


        // THEN
        thenBatchCompleted();
        verify(notiGeneratorAndEnqueuer, times(2)).generateNotiAndEnqueue(any(User.class));

        // ROLLBACK
        rollback.rollback(savedUsers);
    }


}
