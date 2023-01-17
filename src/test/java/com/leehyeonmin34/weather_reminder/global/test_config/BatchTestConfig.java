package com.leehyeonmin34.weather_reminder.global.test_config;

import com.leehyeonmin34.weather_reminder.global.config.TestProfile;
import org.junit.Ignore;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

@Ignore
@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
@ActiveProfiles(TestProfile.TEST)
public class BatchTestConfig { }
