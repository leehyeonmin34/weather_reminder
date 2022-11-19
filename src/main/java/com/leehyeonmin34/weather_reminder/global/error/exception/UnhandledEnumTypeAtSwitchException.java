package com.leehyeonmin34.weather_reminder.global.error.exception;

public class UnhandledEnumTypeAtSwitchException extends BusinessException{

    public UnhandledEnumTypeAtSwitchException(){
        super(ErrorCode.UNHANDLED_ENUM_TYPE_AT_SWITCH);
    }

    public UnhandledEnumTypeAtSwitchException(Class enumClazz, String enumCode){
        super(ErrorCode.INVALID_ENUM_CODE, String.format("switch문에서 처리되지 않은 enum type이 있습니다. enum_class: %s, code: %s", enumClazz.getName(), enumCode));
    }

}
