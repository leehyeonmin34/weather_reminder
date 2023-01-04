package com.leehyeonmin34.weather_reminder.global.api.exception;

import com.leehyeonmin34.weather_reminder.global.error.exception.BusinessException;
import com.leehyeonmin34.weather_reminder.global.error.exception.ErrorCode;

public class OpenApiException extends BusinessException {

    public OpenApiException(){
        super(ErrorCode.API_ERROR);
    }

    public OpenApiException(String statusCode) {
        super(ErrorCode.API_ERROR, String.format("오픈 API에 문제가 있습니다. 코드: %s, 메시지: %s", statusCode, OpenApiResponseStatus.of(statusCode).getMsg()));
    }
}
