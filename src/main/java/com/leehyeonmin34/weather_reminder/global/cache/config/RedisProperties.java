package com.leehyeonmin34.weather_reminder.global.cache.config;

import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ToString
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {

    private final String host;
    private final int port;

    @ConstructorBinding
    public RedisProperties(String host, int port) {
        this.host = host;
        this.port = port;
    }
}
