package com.leehyeonmin34.weather_reminder.global.config;

import com.leehyeonmin34.weather_reminder.global.cache.config.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({RedisProperties.class})
public class AppConfig {
}
