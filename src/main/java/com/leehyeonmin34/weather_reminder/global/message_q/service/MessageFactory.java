package com.leehyeonmin34.weather_reminder.global.message_q.service;

import com.leehyeonmin34.weather_reminder.domain.notification.domain.Notification;
import com.leehyeonmin34.weather_reminder.global.message_q.model.Message;
import com.leehyeonmin34.weather_reminder.global.message_q.model.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MessageFactory {

    private final MessageFailHandler messageFailHandler;

    private Message createMessage(MessageType messageType, Notification notification){
        return new Message(messageType, notification, messageFailHandler);
    }

    public Message createNotiMessage(Notification notification){
        return createMessage(MessageType.NOTI, notification);
    }



}
