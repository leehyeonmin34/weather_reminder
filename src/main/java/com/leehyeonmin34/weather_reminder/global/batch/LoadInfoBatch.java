//package com.leehyeonmin34.weather_reminder.global.batch;
//
//import com.leehyeonmin34.weather_reminder.domain.notification.service.common.NotiGeneratorAndEnqueuer;
//import com.leehyeonmin34.weather_reminder.domain.weather_info.domain.WeatherInfo;
//import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType;
//import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
//import com.leehyeonmin34.weather_reminder.domain.weather_info.service.WeatherApiService;
//import com.leehyeonmin34.weather_reminder.global.TestDomainItemSqlParameterSourceProvider;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.JobScope;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.database.JdbcBatchItemWriter;
//import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
//import org.springframework.batch.item.support.ListItemReader;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//import java.util.Arrays;
//import java.util.List;
//
//@Slf4j
//@RequiredArgsConstructor
//@Configuration
//public class LoadInfoBatch {
//
//    private final NotiGeneratorAndEnqueuer notiGeneratorAndEnqueuer;
//    private final DataSource dataSource;
//    private final WeatherApiService weatherApiService;
//    private final LoadInfoItemSqlParameterSourceProvider loadInfoItemSqlParameterSourceProvider;
//    private final TestDomainItemSqlParameterSourceProvider testDomainItemSqlParameterSourceProvider;
//
//
//    private static final int CHUNK_SIZE = 1;
//
//    @Bean
//    public Job loadInfoJob(JobBuilderFactory jobBuilderFactory, Step loadInfoJobStep){
//        log.info("********** This is loadInfoJob");
//        return jobBuilderFactory.get("loadInfoJob")
//                .incrementer(new RunIdIncrementer()) // 중복실행 가능
////                .preventRestart() // 이 job의 중복실행 방지
//                .start(loadInfoJobStep)
//                .build();
//    }
//
//    @Bean
//    @JobScope
//    public Step loadInfoJobStep(StepBuilderFactory stepBuilderFactory) throws Exception {
//        log.info("********** This is loadInfoJobStep");
//        return stepBuilderFactory.get("loadInfoJobStep")
//                .<WeatherDataType, List<WeatherInfo>> chunk(CHUNK_SIZE)
//                .reader(loadInfoReader())
//                .processor(loadInfoProcessor())
//                .writer(loadInfoWriter())
//                .build();
//    }
//
//    @Bean
//    @StepScope
//    public ListItemReader<WeatherDataType> loadInfoReader() throws Exception {
//        log.info("********** This is loadInfoReader");
//        return new ListItemReader<>(Arrays.asList(WeatherDataType.values()));
//    }
//
//    @Bean
//    @StepScope
//    public ItemProcessor<WeatherDataType, List<WeatherInfo>> loadInfoProcessor() {
//        return new ItemProcessor<WeatherDataType, List<WeatherInfo>>() {
//            @Override
//            public List<WeatherInfo> process(WeatherDataType weatherDataType) throws Exception {
//                log.info("********** This is loadInfoProcessor");
//
////                return List.of(new TestDomain("1"), new TestDomain("2"), new TestDomain("3") );
//
//                return List.of(
//                        new WeatherInfo("202201010101", "202201010101", WeatherRegion.SEOUL, weatherDataType, weatherDataType.getUnit(), 1),
//                        new WeatherInfo("202201010101", "202201010102", WeatherRegion.SEOUL, weatherDataType, weatherDataType.getUnit(), 2)
//                );
////                return Arrays.stream(WeatherRegion.values())
////                        .map(weatherRegion -> weatherApiService.getWeatherInfo(weatherRegion, weatherDataType))
////                        .flatMap(weatherInfo -> weatherInfo.stream())
////                        .collect(Collectors.toList());
//            }
//        };
//    }
//
//    @Bean
//    @StepScope
//    public JdbcBatchListItemWriter<WeatherInfo> loadInfoWriter() {
//        log.info("********** This is loadInfoWriter");
//        String sql = "insert into weather_info(base_time, fcst_time, weather_region, weather_data_type, unit, value) values(:baseTime, :fcstTime, :weatherRegion, :weatherDataType, :unit, :value)";
//        JdbcBatchItemWriter<WeatherInfo> itemWriter = new JdbcBatchItemWriterBuilder<WeatherInfo>()
//                .dataSource(dataSource)
//                .itemSqlParameterSourceProvider(loadInfoItemSqlParameterSourceProvider)
//                .sql(sql)
//                .build();
//        itemWriter.afterPropertiesSet();
//
//        return new JdbcBatchListItemWriter<>(itemWriter);
//    }
//
//
//}
