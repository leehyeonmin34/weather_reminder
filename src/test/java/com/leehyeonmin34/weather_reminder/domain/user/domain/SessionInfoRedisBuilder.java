package com.leehyeonmin34.weather_reminder.domain.user.domain;

import com.leehyeonmin34.weather_reminder.domain.session_info.domain.SessionInfoRedis;

public class SessionInfoRedisBuilder {

    static public SessionInfoRedis build(){
        return SessionInfoRedis.from(SessionInfoBuilder.build());
    }
}
