package com.leehyeonmin34.weather_reminder.domain.user.domain;

import com.leehyeonmin34.weather_reminder.domain.session_info.domain.SessionInfo;

import java.time.LocalDateTime;

public class SessionInfoBuilder {

    public static SessionInfo build(){
        String accessToken = "testAccessToken";
        Long userId = 999L;
        String userAgent = "testUserAgent";
        LocalDateTime expirationTime = LocalDateTime.now();
        SessionInfo sessionInfo = new SessionInfo.Builder(accessToken, userId)
                .userAgent(userAgent)
                .expirationTime(expirationTime)
                .build();
        return sessionInfo;
    }
}
