package com.leehyeonmin34.weather_reminder.global.cache.config;

import com.leehyeonmin34.weather_reminder.domain.session_info.repository.SessionInfoRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CacheEnv {

    final private SessionInfoRepository sessionInfoRepository;

    public static final int ONE_DAY = 60 * 60 * 24;
    public static final int ONE_HOUR = 60 * 60;
    public static final int ONE_MINUTE = 60;

    public static final int DEFAULT_EXPIRE_SEC = ONE_DAY; // 1 day

    public static final String SESSION_INFO = "SessionInfo";
    public static final int SESSION_INFO_EXPIRE_SEC = ONE_DAY;

    public static final String WEATHER_MSG_COLD = "WeatherMsgCold";
    public static final String WEATHER_MSG_HOT = "WeatherMsgHot";
    public static final String WEATHER_MSG_RAIN = "WeatherMsgRain";
    public static final int WEATHER_MSG_EXPIRE_SEC = ONE_DAY;

    public static final String TODAY_WEATHER_INFO_LIST = "TodayWeatherInfo";
    public static final int TODAY_WEATHER_INFO_LIST_EXPIRE_SEC = 2 * ONE_DAY;




}
