package com.leehyeonmin34.weather_reminder.global.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST.value(), "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED.value(), "C002", " Method not allowed"),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "C003", "Entity Not Found"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "C004", "Server Error"),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST.value(), "C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN.value(), "C006", "Access is Denied"), // Authentication 객체가 필요한 권한을 보유하지 않은 경우 발생
    INVALID_ENUM_CODE(HttpStatus.BAD_REQUEST.value(), "C007", "Invalid Enum Code"),
    UNHANDLED_ENUM_TYPE_AT_SWITCH(HttpStatus.INTERNAL_SERVER_ERROR.value(), "C008", "Unhandler Enum Type at Switch"),

    // User
    NOT_LOGGED_IN(HttpStatus.UNAUTHORIZED.value(), "U002", "로그인되지 않았습니다."),

    // Session
    ACCESSED_EXPIRED_SESSION_TOKEN(HttpStatus.BAD_REQUEST.value(), "S001", "만료된 세션 정보에 접근했습니다."),
    SESSION_INFO_NOT_EXISTS(HttpStatus.BAD_REQUEST.value(), "S002", "해당 토큰에 해당하는 세션 정보가 존재하지 않습니다."),

    // NotiSetting
    INVALID_NOTI_SETTING_REQUEST(HttpStatus.INTERNAL_SERVER_ERROR.value(), "N001", "정상적이지 않은 NotiSettingRequest 파라미터입니다."),
    ;

    private int status;
    private final String code;
    private final String type;

    ErrorCode(final int status, final String code, final String message) {
        this.code = code;
        this.status = status;
        this.type = message;
    }

    public String getType() {
        return this.type;
    }
    public int getStatus() { return this.status; }
    public String getCode() { return this.code; }
}