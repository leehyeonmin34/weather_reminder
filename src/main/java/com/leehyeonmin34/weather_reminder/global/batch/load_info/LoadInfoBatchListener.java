package com.leehyeonmin34.weather_reminder.global.batch.load_info;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
@NoArgsConstructor
public class LoadInfoBatchListener implements JobExecutionListener {
    @Override
    public void beforeJob(final JobExecution jobExecution) { }

    @Override
    public void afterJob(final JobExecution jobExecution) {
        long time = jobExecution.getEndTime().getTime() - jobExecution.getStartTime().getTime();

        log.info("날씨 정보 조회 배치 프로그램");
        log.info("----------------------");
        log.info("총 소요시간 {}mills", time);

    }
}
