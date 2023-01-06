package com.leehyeonmin34.weather_reminder.global.message_q.model;

import com.leehyeonmin34.weather_reminder.domain.notification.domain.Notification;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Message<T> {

    private MessageType messageType;

    private T content;

    // content에 아무 클래스나 들어올 수 있다면 handler에서 문제 발생 가능
    // 팩토리 메서드로 content 타입을 강제
    public static Message<Notification> of(Notification content){
        assert content instanceof Notification;
        return new Message(MessageType.NOTI, content);
    }

    private Message(MessageType messageType, T content){
        this.messageType = messageType;
        this.content = content;
    }

}
