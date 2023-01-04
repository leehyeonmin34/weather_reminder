package com.leehyeonmin34.weather_reminder.domain.notification.service.weather;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

@AllArgsConstructor
@Getter
@ToString
public enum RainLevel {

    NO("비 안 옴", Float.MIN_VALUE, 3f),
    WEAK("약한 비", 3f, 6f),
    NORMAL("비", 6f, 15f),
    STRONG("강한 비", 15f, 20f),
    VERY_STRONG("매우 강한 비", 20f, Float.MAX_VALUE),
    ;

    private final String desc;
    private final float min;
    private final float max;

    public static RainLevel of(final float precipitation){
        return Arrays.stream(RainLevel.values()).filter(
                item -> item.getMax() > precipitation && item.getMin() <= precipitation
            ).findAny().get();
    }

    public static boolean isRain(final float precipitation){
        return precipitation >= NO.getMax();
    }



}

// https://m.blog.naver.com/kma_131/222073284939
// 강수량 수준 참고