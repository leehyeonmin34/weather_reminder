package com.leehyeonmin34.weather_reminder.domain.session_info.service;


import com.leehyeonmin34.weather_reminder.domain.session_info.domain.SessionInfo;

public interface SessionService {
    boolean existsByToken(String accessToken);

    SessionInfo getSessionElseThrow(String accessToken);

    SessionInfo registerSession(UserGetResponse user, String userAgent);

    void removeSession(String accessToken);
}
