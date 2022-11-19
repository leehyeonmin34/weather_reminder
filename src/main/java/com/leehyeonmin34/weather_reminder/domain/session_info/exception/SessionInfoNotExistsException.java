package com.leehyeonmin34.weather_reminder.domain.session_info.exception;


import com.leehyeonmin34.weather_reminder.global.error.exception.BusinessException;
import com.leehyeonmin34.weather_reminder.global.error.exception.ErrorCode;

public class SessionInfoNotExistsException extends BusinessException {

    public SessionInfoNotExistsException() {
        super(ErrorCode.SESSION_INFO_NOT_EXISTS);
    }

}
