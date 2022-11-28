package com.leehyeonmin34.weather_reminder.domain.notification.service.weather;

import com.leehyeonmin34.weather_reminder.domain.notification.service.common.MessageGenerator;
import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.domain.weather_info.domain.WeatherInfo;

public interface WeatherMessageGenerator extends MessageGenerator {
    String generate(User user, WeatherInfo weatherInfo);
}
