package com.leehyeonmin34.weather_reminder.domain.notification.service.weather;

import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.domain.weather_info.domain.WeatherInfo;
import org.springframework.stereotype.Component;

@Component
public class RainMessageGenerator implements WeatherMessageGenerator{
    @Override
    public String generate(User user, WeatherInfo weatherInfo) {
        return "비 메시지";
    }
}
