package com.leehyeonmin34.weather_reminder.domain.user.model;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class ColdNotiSetting implements NotiSetting {

    private Byte conditionCelcius = null;

    private String message = "오늘은 너무 추운 날이에요! 옷을 따뜻하게 입거나 핫팩을 챙겨가세요!";

    public void changeConditionCelcius(Byte conditionTime){
        this.conditionCelcius = conditionTime;
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