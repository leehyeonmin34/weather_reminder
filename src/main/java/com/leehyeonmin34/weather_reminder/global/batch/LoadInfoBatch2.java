package com.leehyeonmin34.weather_reminder.global.batch;

import com.leehyeonmin34.weather_reminder.domain.notification.service.common.NotiGeneratorAndEnqueuer;
import com.leehyeonmin34.weather_reminder.domain.weather_info.domain.WeatherInfo;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
import com.leehyeonmin34.weather_reminder.domain.weather_info.service.WeatherApiService;
import com.leehyeonmin34.weather_reminder.global.TestDomain;
import com.leehyeonmin34.weather_reminder.global.TestDomainItemSqlParameterSourceProvider;
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
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class LoadInfoBatch2 {

    private final NotiGeneratorAndEnqueuer notiGeneratorAndEnqueuer;
    private final DataSource dataSource;
    private final WeatherApiService weatherApiService;
    private final LoadInfoItemSqlParameterSourceProvider loadInfoItemSqlParameterSourceProvider;
    private final TestDomainItemSqlParameterSourceProvider testDomainItemSqlParameterSourceProvider;

    private static final int CHUNK_SIZE = 1;

    @Bean
    public Job loadInfoJob(JobBuilderFactory jobBuilderFactory, Step loadInfoJobStep){
        log.info("********** This is loadInfoJob");
        return jobBuilderFactory.get("loadInfoJob")
                .incrementer(new RunIdIncrementer()) // 중복실행 가능
//                .preventRestart() // 이 job의 중복실행 방지
                .start(loadInfoJobStep)
                .build();
    }

//    @Bean(name = "test_domain_job_step")
    @Bean
    @JobScope
    public Step loadInfoJobStep(StepBuilderFactory stepBuilderFactory) throws Exception {
        log.info("********** This is loadInfoJobStep");
        return stepBuilderFactory.get("loadInfoJobStep")
                .<WeatherDataType, List<TestDomain>> chunk(CHUNK_SIZE)
                .reader(loadInfoReader())
                .processor(loadInfoProcessor())
                .writer(loadInfoWriter())
                .build();
    }

    @Bean
//    @Bean(name = "test_domain_job_reader")
    @StepScope
    public ListItemReader<WeatherDataType> loadInfoReader() throws Exception {
        log.info("********** This is loadInfoReader");
        return new ListItemReader<>(Arrays.asList(WeatherDataType.values()));
    }

    @Bean
//    @Bean(name = "test_domain_job_processor")
    @StepScope
    public ItemProcessor<WeatherDataType, List<TestDomain>> loadInfoProcessor() {
        return new ItemProcessor<WeatherDataType, List<TestDomain>>() {
            @Override
            public List<TestDomain> process(WeatherDataType weatherDataType) throws Exception {
                log.info("********** This is loadInfoProcessor");

                return List.of(
                        new TestDomain(null, "1", LocalDateTime.now(), 999999, 999999, 999999, 999999, 999999, 1),
                        new TestDomain(null, "2", LocalDateTime.now(), 999999, 999999, 999999, 999999, 999999, 1)
//                        new TestDomain(null, "3", LocalDateTime.now(), 999999, 999999, 999999, 999999, 999999, 1)
                        );
            }
        };
    }




    @Bean
//    @Bean(name = "test_domain_job_writer")
    @StepScope
    public JdbcBatchListItemWriter<TestDomain> loadInfoWriter() {
        log.info("********** This is loadInfoWriter");
        String sql = "insert into test_domain(name, time, column2, column3, column4, column5, column6, column7) values(:name, :time, :column2, :column3, :column4, :column5, :column6, :column7)";
        JdbcBatchItemWriter<TestDomain> itemWriter = new JdbcBatchItemWriterBuilder<TestDomain>()
                .dataSource(dataSource)
                .itemSqlParameterSourceProvider(testDomainItemSqlParameterSourceProvider)
                .sql(sql)
                .build();
        itemWriter.afterPropertiesSet();

        return new JdbcBatchListItemWriter<>(itemWriter);
    }


}