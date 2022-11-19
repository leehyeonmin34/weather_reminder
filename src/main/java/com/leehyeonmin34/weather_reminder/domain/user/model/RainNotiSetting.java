package com.leehyeonmin34.weather_reminder.domain.user.model;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class RainNotiSetting implements NotiSetting {

    private Byte conditionTime = null;

    private String message = "오늘은 비가 올 거에요! 우산을 챙겨가시고, 비에 젖어도 괜찮은 옷을 입으세요!";

    public void changeConditionTime(Byte conditionTime) {
        this.conditionTime = conditionTime;
    }


    @Override
    public void turnOff() {
        this.conditionTime = null;
    }
    @Override
    public boolean isOn(){
        return this.conditionTime != null;
    }
}
