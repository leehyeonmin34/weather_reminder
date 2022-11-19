package com.leehyeonmin34.weather_reminder.domain.session_info.exception;


import com.leehyeonmin34.weather_reminder.global.error.exception.BusinessException;
import com.leehyeonmin34.weather_reminder.global.error.exception.ErrorCode;

public class AccessedExpiredSessionTokenException extends BusinessException {

    public AccessedExpiredSessionTokenException() {
        super(ErrorCode.ACCESSED_EXPIRED_SESSION_TOKEN);
    }

}
