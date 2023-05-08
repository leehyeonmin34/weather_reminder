package com.leehyeonmin34.weather_reminder;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableBatchProcessing // 배치 애플리케이션을 구동하는데 필요한 설정을 자동으로 등록시켜준다
@EnableScheduling // 스케줄링 기능을 사용가능하게 해줌
@SpringBootApplication
public class WeatherReminderApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherReminderApplication.class, args);
	}

}
