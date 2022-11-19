package com.leehyeonmin34.weather_reminder.domain.user.model;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class HotNotiSetting implements NotiSetting {

    private Byte conditionCelcius = null;

    private String message = "오늘은 너무 더운 날이에요! 얇은 옷이나 양산을 준비하시고 선크림도 잘 발라주세요!";

    public void changeConditionCelcius(Byte conditionCelcius) {
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
