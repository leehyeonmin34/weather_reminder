package com.leehyeonmin34.weather_reminder.domain.notification.service.weather;

import com.leehyeonmin34.weather_reminder.domain.notification.model.NotiContentType;
import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherInfoList;

public interface WeatherTypeNotiStringGenerator {

    NotiContentType getNotiContentType();
    String generate(User user, WeatherInfoList weatherInfoList);
}
