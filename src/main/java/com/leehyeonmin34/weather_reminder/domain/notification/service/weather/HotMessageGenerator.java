package com.leehyeonmin34.weather_reminder.domain.notification.service.weather;

import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.domain.weather_info.domain.WeatherInfo;
import org.springframework.stereotype.Component;

@Component
public class HotMessageGenerator implements WeatherMessageGenerator{
    @Override
    public String generate(User user, WeatherInfo weatherInfo) {
        return "더운 날 메시지";
    }
}
