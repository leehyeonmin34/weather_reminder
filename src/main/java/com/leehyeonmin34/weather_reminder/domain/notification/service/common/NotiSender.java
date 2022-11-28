package com.leehyeonmin34.weather_reminder.domain.notification.service.common;

import com.leehyeonmin34.weather_reminder.domain.notification.domain.Notification;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotiSender {

    final private NotiQ notiQ;

    public void send(){
        Notification noti = notiQ.poll();

        // TODO - 채팅방으로 보내는 로직
    }
}
