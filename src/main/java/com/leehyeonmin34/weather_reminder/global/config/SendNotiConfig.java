package com.leehyeonmin34.weather_reminder.global.config;

import com.leehyeonmin34.weather_reminder.domain.notification.domain.Notification;
import com.leehyeonmin34.weather_reminder.domain.notification.repository.NotificationRepository;
import com.leehyeonmin34.weather_reminder.domain.notification.service.common.NotiGeneratorAndSender;
import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Configuration
public class SendNotiConfig {

    private UserRepository userRepository;
    private NotificationRepository notificationRepository;
    private NotiGeneratorAndSender notiGeneratorAndSender;

    @Bean
    public Job sendNotiJob(JobBuilderFactory jobBuilderFactory, Step sendNotiJobStep){
        log.info("********** This is sendNotiJob");
        return jobBuilderFactory.get("sendNotiJob")
                .preventRestart() // 이 job의 중복실행 방지
                .start(sendNotiJobStep)
                .build();
    }

    @Bean
    public Step sendNotiJobStep(StepBuilderFactory stepBuilderFactory){
        log.info("********** This is sendNotiJobStep");
        return stepBuilderFactory.get("sendNotiJob")
                .<User, Notification> chunk(100)
                .reader(sendNotiReader())
                .processor(sendNotiProcessor(notiGeneratorAndSender))
                .writer(sendNotiWriter())
                .build();
    }

    @Bean
    @StepScope
    public ListItemReader<User> sendNotiReader() {
        log.info("********** This is sendNotiReader");
        List<User> users = userRepository.findAll();
        log.info("          - users SIZE : " + users.size());
        return new ListItemReader<>(users);
    }

    @Bean
    @StepScope
    public ItemProcessor<User, Notification> sendNotiProcessor(NotiGeneratorAndSender notiGeneratorAndSender) {
        return new ItemProcessor<User, Notification>() {
            @Override
            public Notification process(User user) throws Exception {
                log.info("********** This is sendNotiProcessor");
                return notiGeneratorAndSender.generateNotiAndSend(user);
            }
        };
    }


    @Bean
    @StepScope
    public ItemWriter<Notification> sendNotiWriter() {
        log.info("********** This is sendNotiWriter");
        return ((List<? extends Notification> notiList) ->
                notificationRepository.saveAll(notiList));
    }


}
