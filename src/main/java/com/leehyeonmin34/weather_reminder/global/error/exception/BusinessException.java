package com.leehyeonmin34.weather_reminder.global.error.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode){
        super(errorCode.getType());
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode, String msg){
        super(msg);
        this.errorCode = errorCode;
    }

}