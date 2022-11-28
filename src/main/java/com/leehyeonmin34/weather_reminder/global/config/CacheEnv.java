package com.leehyeonmin34.weather_reminder.global.config;

import com.leehyeonmin34.weather_reminder.domain.session_info.repository.SessionInfoRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CacheEnv {

    final private SessionInfoRepository sessionInfoRepository;

    public static final int ONE_DAY = 60 * 60 * 24;
    public static final int DEFAULT_EXPIRE_SEC = ONE_DAY; // 1 day
    public static final String SESSION_INFO = "SessionInfo";
    public static final String TEST = "test";
    public static final int SESSION_INFO_EXPIRE_SEC = ONE_DAY;




}
