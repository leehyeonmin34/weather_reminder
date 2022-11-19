package com.leehyeonmin34.weather_reminder.domain.user.exception;

import com.leehyeonmin34.weather_reminder.global.error.exception.BusinessException;
import com.leehyeonmin34.weather_reminder.global.error.exception.ErrorCode;

public class InvalidNotiSettingRequestException extends BusinessException {

    public InvalidNotiSettingRequestException(){
        super(ErrorCode.INVALID_NOTI_SETTING_REQUEST);
    }

}
