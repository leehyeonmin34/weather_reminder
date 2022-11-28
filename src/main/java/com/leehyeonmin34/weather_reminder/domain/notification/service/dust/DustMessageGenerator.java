package com.leehyeonmin34.weather_reminder.domain.notification.service.dust;

import com.leehyeonmin34.weather_reminder.domain.dust_info.domain.DustInfo;
import com.leehyeonmin34.weather_reminder.domain.notification.service.common.MessageGenerator;
import com.leehyeonmin34.weather_reminder.domain.user.domain.User;

public interface DustMessageGenerator extends MessageGenerator {

    String generate(User user, DustInfo dustInfo);

}
