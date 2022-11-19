package com.leehyeonmin34.weather_reminder.domain.session_info.service;

import com.leehyeonmin34.weather_reminder.domain.session_info.domain.SessionInfo;
import com.leehyeonmin34.weather_reminder.global.config.CacheEnv;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class SessionInfoCacheService {

    @CachePut(value = CacheEnv.SESSION_INFO, key = "#accessToken", unless = "#result == null")
    public SessionInfo saveSessionInfoCache(String accessToken, SessionInfo sessionInfo){
        return sessionInfo;
    }
}
