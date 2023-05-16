package com.leehyeonmin34.weather_reminder.global.batch.load_info;

import com.leehyeonmin34.weather_reminder.domain.user.model.Region;
import com.leehyeonmin34.weather_reminder.domain.weather_info.domain.WeatherInfo;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType;
import com.leehyeonmin34.weather_reminder.domain.weather_info.service.WeatherApiService;
import com.leehyeonmin34.weather_reminder.global.batch.comon.JdbcBatchListItemWriter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class AsyncLoadInfoBatch {

    public final static String JOB_NAME = "async";
    private final DataSource dataSource;
    private final WeatherApiService weatherApiService;
    private final LoadInfoItemSqlParameterSourceProvider loadInfoItemSqlParameterSourceProvider;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final TaskExecutor taskExecutor;


    public static final int CHUNK_SIZE = 100;

    @Bean(JOB_NAME + "_loadInfoJob")
    public Job loadInfoJob() throws Exception {
        log.info("********** This is loadInfoJob");
        return jobBuilderFactory.get(JOB_NAME + "_loadInfoJob")
                .incrementer(new RunIdIncrementer()) // 중복실행 가능
                .listener(new LoadInfoBatchListener())
//                .preventRestart() // 이 job의 중복실행 방지
                .start(this.loadInfoJobStep())
                .build();
    }

    @Bean(JOB_NAME + "_loadInfoJobStep")
    @JobScope
    public Step loadInfoJobStep() throws Exception {
        log.info("********** loadInfoJobStep created");
        return stepBuilderFactory.get(JOB_NAME + "_loadInfoJobStep")
                .<RegionAndDataType, Future<List<WeatherInfo>>> chunk(CHUNK_SIZE)
                .reader(loadInfoReader())
                .processor(loadInfoProcessor())
                .writer(loadInfoWriter())
                .build();
    }

    @Bean(JOB_NAME + "loadInfoReader")
    @StepScope
    public ListItemReader<RegionAndDataType> loadInfoReader() throws Exception {
        log.info("********** loadInfoReader created");

        // (Region, WeatherDataType) 데카르트 곱 조합
        return new ListItemReader<>(
            Stream.of(Region.values())
                .flatMap(region ->
                    Stream.of(WeatherDataType.values())
                            .map(weatherDataType -> new RegionAndDataType(region, weatherDataType)))
                .collect(Collectors.toList()));
    }


    @Bean(JOB_NAME + "_loadInfoProcessor")
    @StepScope
    public AsyncItemProcessor<RegionAndDataType, List<WeatherInfo>> loadInfoProcessor(){
        ItemProcessor<RegionAndDataType, List<WeatherInfo>> itemProcessor = normalLoadInfoProcessor();

        AsyncItemProcessor<RegionAndDataType, List<WeatherInfo>> asyncItemProcessor = new AsyncItemProcessor<>();
        asyncItemProcessor.setDelegate(itemProcessor);
        asyncItemProcessor.setTaskExecutor(taskExecutor);

        return asyncItemProcessor;
    }

    private ItemProcessor<RegionAndDataType, List<WeatherInfo>> normalLoadInfoProcessor() {
        return new ItemProcessor<RegionAndDataType, List<WeatherInfo>>() {
            @Override
            public List<WeatherInfo> process(RegionAndDataType regionAndDataType) throws Exception {
                log.info("********** processing by loadInfoProcessor");
                return weatherApiService.getWeatherInfo(regionAndDataType.getRegion(), regionAndDataType.getWeatherDataType());
            }
        };
    }


    @Bean(JOB_NAME + "_loadInfoWriter")
    @StepScope
    public AsyncItemWriter<List<WeatherInfo>> loadInfoWriter() {
        ItemWriter<List<WeatherInfo>> itemWriter = normalLoadInfoWriter();

        AsyncItemWriter<List<WeatherInfo>> asyncItemWriter = new AsyncItemWriter<>();
        asyncItemWriter.setDelegate(itemWriter);

        return asyncItemWriter;
    }

    public ItemWriter<List<WeatherInfo>> normalLoadInfoWriter() {
        log.info("********** loadInfoWriter Created");
        String sql = "insert into weather_info(base_time, fcst_time, weather_region, data_type, val) values(:baseTime, :fcstTime, :weatherRegion, :weatherDataType, :value)";
        JdbcBatchItemWriter<WeatherInfo> itemWriter = new JdbcBatchItemWriterBuilder<WeatherInfo>()
                .dataSource(dataSource)
                .itemSqlParameterSourceProvider(loadInfoItemSqlParameterSourceProvider)
                .sql(sql)
                .build();
        itemWriter.afterPropertiesSet();

//        return itemWriter;
        return new JdbcBatchListItemWriter<>(itemWriter);
    }




    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class RegionAndDataType{
        private final Region region;
        private final WeatherDataType weatherDataType;
    }


}
