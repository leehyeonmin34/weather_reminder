package com.leehyeonmin34.weather_reminder.global.batch.load_info;

import com.leehyeonmin34.weather_reminder.domain.notification.service.common.NotiGeneratorAndEnqueuer;
import com.leehyeonmin34.weather_reminder.domain.weather_info.builder.WeatherInfoListBuilder;
import com.leehyeonmin34.weather_reminder.domain.weather_info.domain.QWeatherInfo;
import com.leehyeonmin34.weather_reminder.domain.weather_info.domain.WeatherInfo;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherDataTypeConverter;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherRegionConverter;
import com.leehyeonmin34.weather_reminder.domain.weather_info.service.WeatherApiService;
import com.leehyeonmin34.weather_reminder.global.test_config.BatchTestSupport;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class LoadInfoBatchTest extends BatchTestSupport{

    private final static String NORMAL = "normal";
    private final static String JOB_NAME = NORMAL;

    @Autowired
    @Qualifier(JOB_NAME + "_loadInfoJob")
    private Job loadInfoJob;

    @MockBean(name = JOB_NAME + "_loadInfoProcessor")
    private ItemProcessor<LoadInfoBatch.RegionAndDataType, List<WeatherInfo>> loadInfoProcessor;

    @Autowired
    private JPAQueryFactory query;

    @MockBean
    private MysqlDataSource dataSource;

    @SpyBean
    private LoadInfoItemSqlParameterSourceProvider loadInfoItemSqlParameterSourceProvider;

    @SpyBean
    private WeatherRegionConverter weatherRegionConverter;

    @SpyBean
    private WeatherDataTypeConverter weatherDataTypeConverter;

    @MockBean
    private NotiGeneratorAndEnqueuer notiGeneratorAndEnqueuer;

    @MockBean
    private WeatherApiService weatherApiService;

    @Autowired
    LoadInfoBatchRollback rollback;

    @Override
    protected Job getJob() {
        return loadInfoJob;
    }

    @AfterEach
    private void rollback(){
        rollback.rollback();
    }

    @Test
    public void writeTest() throws Exception {

        // GIVEN
        List<WeatherInfo> seoulTempInfoList = WeatherInfoListBuilder.build(WeatherRegion.SEOUL, WeatherDataType.TEMP, 99999).getTempWeatherInfoList();
        List<WeatherInfo> busanTempInfoList = WeatherInfoListBuilder.build(WeatherRegion.BUSAN, WeatherDataType.TEMP, 99999).getTempWeatherInfoList();
        List<WeatherInfo> seoulRainInfoList = WeatherInfoListBuilder.build(WeatherRegion.SEOUL, WeatherDataType.RAIN, 99999).getRainWeatherInfoList();
        List<WeatherInfo> busanRainInfoList = WeatherInfoListBuilder.build(WeatherRegion.BUSAN, WeatherDataType.RAIN, 99999).getRainWeatherInfoList();
        when(loadInfoProcessor.process(any(LoadInfoBatch.RegionAndDataType.class)))
                .thenReturn(seoulTempInfoList, busanTempInfoList, seoulRainInfoList, busanRainInfoList, Collections.emptyList(), Collections.emptyList());

        // WHEN
        launchJob(loadInfoJob);

        // THEN
        final int savedWeatherInfoPerRequest = 19;
        final int requestNum = 4;

        thenBatchCompleted();
        then(jobExecution.getStepExecutions().stream().count()).isEqualTo(1);
        then(jobExecution.getStepExecutions().stream()
                .mapToInt(StepExecution::getWriteCount)
                .sum())
                .isEqualTo(4);
        then(jobExecution.getStepExecutions().stream().mapToInt(StepExecution::getCommitCount)
                .sum()).isEqualTo(requestNum % LoadInfoBatch.CHUNK_SIZE > 0 ? requestNum / LoadInfoBatch.CHUNK_SIZE + 1 : requestNum / LoadInfoBatch.CHUNK_SIZE);

        then(query
            .select(QWeatherInfo.weatherInfo.count())
            .where(QWeatherInfo.weatherInfo.value.gt(99900))
            .from(QWeatherInfo.weatherInfo)
            .fetchOne()).isEqualTo(savedWeatherInfoPerRequest * requestNum);
    }
}