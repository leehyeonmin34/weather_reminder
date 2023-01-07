package com.leehyeonmin34.weather_reminder.global.batch;

import com.leehyeonmin34.weather_reminder.domain.notification.domain.Notification;
import com.leehyeonmin34.weather_reminder.domain.notification.service.common.NotiGeneratorAndEnqueuer;
import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SendNotiBatch {

    private final NotiGeneratorAndEnqueuer notiGeneratorAndEnqueuer;
    private final EntityManagerFactory entityManagerFactory;
    private final DataSource dataSource;

    private static final int CHUNK_SIZE = 100;

//    @Bean
    public Job sendNotiJob(JobBuilderFactory jobBuilderFactory, Step sendNotiJobStep){
        log.info("********** This is sendNotiJob");
        return jobBuilderFactory.get("sendNotiJob")
                .incrementer(new RunIdIncrementer()) // 중복실행 가능
//                .preventRestart() // 이 job의 중복실행 방지
                .start(sendNotiJobStep)
                .build();
    }

    @Bean
    @JobScope
    public Step sendNotiJobStep(StepBuilderFactory stepBuilderFactory) throws Exception {
        log.info("********** This is sendNotiJobStep");
        return stepBuilderFactory.get("sendNotiJobStep")
                .<User, Notification> chunk(CHUNK_SIZE)
                .reader(sendNotiReader(null))
                .processor(sendNotiProcessor(notiGeneratorAndEnqueuer))
                .writer(sendNotiWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<User> sendNotiReader(@Value("#{jobParameters[notiTimeString]}") String notiTimeString) throws Exception {
        log.info("********** This is sendNotiReader");
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("notiTime", notiTimeString);

        JpaPagingItemReader<User> itemReader = new JpaPagingItemReaderBuilder<User>()
                .name("userReader")
                .pageSize(CHUNK_SIZE)
                .entityManagerFactory(entityManagerFactory)
                .queryString("select u from User u where noti_time = :notiTime")
                .parameterValues(parameterValues)
                .build();
        itemReader.afterPropertiesSet();
        return itemReader;
    }

    @Bean
    @StepScope
    public ItemProcessor<User, Notification> sendNotiProcessor(NotiGeneratorAndEnqueuer notiGeneratorAndEnqueuer) {
        return new ItemProcessor<User, Notification>() {
            @Override
            public Notification process(User user) throws Exception {
                log.info("********** This is sendNotiProcessor");
                log.info(user.toString());
                notiGeneratorAndEnqueuer.generateNotiAndEnqueue(user);
                return null;
            }
        };
    }


    @Bean
    @StepScope
    public ItemWriter<Notification> sendNotiWriter() {
        log.info("********** This is sendNotiWriter");
        JdbcBatchItemWriter<Notification> itemWriter = new JdbcBatchItemWriterBuilder<Notification>()
                .dataSource(dataSource)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("insert into notification(id, msg, user_id) values(:id, :msg, :userId)")
                .build();
        itemWriter.afterPropertiesSet();
        return itemWriter;
    }


}
