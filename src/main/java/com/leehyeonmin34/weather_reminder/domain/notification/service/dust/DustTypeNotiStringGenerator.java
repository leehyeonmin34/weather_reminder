package com.leehyeonmin34.weather_reminder.domain.notification.service.dust;

import com.leehyeonmin34.weather_reminder.domain.dust_info.domain.DustInfo;
import com.leehyeonmin34.weather_reminder.domain.user.domain.User;

public interface DustTypeNotiStringGenerator {

    String generate(User user, DustInfo dustInfo);

}
