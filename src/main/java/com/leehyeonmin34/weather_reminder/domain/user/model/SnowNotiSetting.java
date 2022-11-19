package com.leehyeonmin34.weather_reminder.domain.user.model;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class SnowNotiSetting implements NotiSetting {

    private Byte conditionTime = null;

    private String message = "오늘은 눈이 올거에요! 미끄럽지 않은 신발을 신고, 눈이 많이 오면 차가 막히니 대중교통을 고려해보세요!";

    public void changeConditionTime(Byte time){
        this.conditionTime = time;
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
