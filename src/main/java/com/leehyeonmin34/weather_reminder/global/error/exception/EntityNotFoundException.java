package com.leehyeonmin34.weather_reminder.global.error.exception;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException(String entityName, Object key){
        super(ErrorCode.ENTITY_NOT_FOUND, String.format("%s 엔티티(id=%s)를 찾을 수 없습니다.", entityName, key.toString()));
    }
}
