package com.leehyeonmin34.weather_reminder.global.error.exception;

public class InvalidEnumCodeException extends BusinessException{

    public InvalidEnumCodeException(){
        super(ErrorCode.INVALID_ENUM_CODE);
    }

    public InvalidEnumCodeException(Class enumClazz, String enumCode){
        super(ErrorCode.INVALID_ENUM_CODE, String.format("잘못된 enum code입니다. enum_class: %s, code: %s", enumClazz.getName(), enumCode));
    }

}
