package com.leehyeonmin34.weather_reminder.domain.user.exception;


import com.leehyeonmin34.weather_reminder.global.error.exception.BusinessException;
import com.leehyeonmin34.weather_reminder.global.error.exception.ErrorCode;

public class NotLoggedInException extends BusinessException {
    public NotLoggedInException() {
        super(ErrorCode.NOT_LOGGED_IN, "로그인되지 않았습니다.");
    }
}
