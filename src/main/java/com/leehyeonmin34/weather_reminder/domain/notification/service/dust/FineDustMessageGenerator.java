package com.leehyeonmin34.weather_reminder.domain.notification.service.dust;

import com.leehyeonmin34.weather_reminder.domain.dust_info.domain.DustInfo;
import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class FineDustMessageGenerator implements DustMessageGenerator{

    @Override
    public String generate(User user, DustInfo dustInfo) {
        return "미세먼지 메시지";
    }
}
