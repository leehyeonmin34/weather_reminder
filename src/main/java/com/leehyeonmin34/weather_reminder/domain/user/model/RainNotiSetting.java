package com.leehyeonmin34.weather_reminder.domain.user.model;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class RainNotiSetting implements NotiSetting {

    private Byte conditionTime = null;

    public void changeConditionTime(final Byte conditionTime) {
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
