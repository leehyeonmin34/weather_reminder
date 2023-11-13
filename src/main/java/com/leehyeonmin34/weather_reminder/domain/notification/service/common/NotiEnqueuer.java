package com.leehyeonmin34.weather_reminder.domain.notification.service.common;

import com.leehyeonmin34.weather_reminder.domain.notification.domain.Notification;
import com.leehyeonmin34.weather_reminder.global.message_q.model.Message;
import com.leehyeonmin34.weather_reminder.global.message_q.service.MessageFactory;
import com.leehyeonmin34.weather_reminder.global.message_q.service.MessageQ;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class NotiEnqueuer {
    final private MessageQ messageQ;
    final private MessageFactory messageFactory;

    public Optional<Message<Notification>> enqueue(Notification noti){
        if (noti == null) return Optional.empty();
        Message<Notification> msg = messageFactory.createNotiMessage(noti);
        messageQ.add(msg);;
        return Optional.of(msg);
    }
}
