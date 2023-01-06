package com.leehyeonmin34.weather_reminder.domain.notification.service.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotiSendScheduler {

    private final JobLauncher jobLauncher;

    private final JobExplorer jobExplorer;

    @Qualifier("sendNotiJob")
    private final Job sendNotiJob;

    @Scheduled(cron = "0/5 * * * * *") // TODO - 10분으로 조정
    public void sendMessage() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        log.info(LocalDateTime.now().toString());
        jobLauncher.run(sendNotiJob, new JobParametersBuilder(jobExplorer) // jobExplorer - 메타 테이블에 대한 read only 쿼리 기능을 위한 인터페이스, runId 조회 가능
                .getNextJobParameters(sendNotiJob)
                .toJobParameters());
    }


}
