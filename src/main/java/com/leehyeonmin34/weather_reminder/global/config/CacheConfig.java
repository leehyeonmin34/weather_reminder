package com.leehyeonmin34.weather_reminder.global.config;

import com.leehyeonmin34.auth_practice.domain.user.domain.SessionInfo;
import com.leehyeonmin34.auth_practice.domain.user.exception.SessionInfoNotExistsException;
import com.leehyeonmin34.auth_practice.domain.user.repository.SessionInfoRepository;
import com.leehyeonmin34.auth_practice.global.common.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;

@Configuration
@RequiredArgsConstructor
public class CacheConfig {

    RedisCacheManager cacheManager;
    SessionInfoRepository sessionInfoRepository;

    @Bean(name = "sessionInfoCacheService")
    public CacheService sessionInfoCacheService(){
        Cache cache = cacheManager.getCache(CacheEnv.SESSION_INFO);
        return new CacheService.Builder<String, SessionInfo>(cache, sessionInfoRepository)
                .exceptionSupplier(SessionInfoNotExistsException::new)
                .build();

    }

}
