package com.leehyeonmin34.weather_reminder.domain.notification.service.common;

import com.leehyeonmin34.weather_reminder.domain.user.domain.User;

public interface NotiStringGenerator {
     String generateMessage(User user);
}
