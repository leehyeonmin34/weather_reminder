package com.leehyeonmin34.weather_reminder.domain.user.model;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class ColdNotiSetting implements NotiSetting {

    private Byte conditionCelcius = null;

    public void changeConditionCelcius(Byte conditionCelcius){
        this.conditionCelcius = conditionCelcius;
    }

    @Override
    public void turnOff() {
        this.conditionCelcius = null;
    }

    @Override
    public boolean isOn(){
        return this.conditionCelcius != null;
    }

}