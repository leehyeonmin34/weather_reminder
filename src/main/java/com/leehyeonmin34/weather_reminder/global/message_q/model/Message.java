package com.leehyeonmin34.weather_reminder.global.message_q.model;

import com.leehyeonmin34.weather_reminder.domain.notification.domain.Notification;
import com.leehyeonmin34.weather_reminder.global.message_q.service.MessageFailHandler;
import com.leehyeonmin34.weather_reminder.global.message_q.service.MessageQ;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@Slf4j
public class Message<T> {

    private MessageFailHandler failHandler;

    private MessageType messageType;

    private T content;

    private int failCount = 0;

    private int tolerance = 3;

    private List<Exception> exceptionList = new ArrayList<>();


    // content에 아무 클래스나 들어올 수 있다면 handler에서 문제 발생 가능
    // 팩토리 메서드로 content 타입을 강제
    public static Message<Notification> of(Notification content, MessageFailHandler messageFailHandler){
        assert content instanceof Notification;
        return new Message(MessageType.NOTI, content, messageFailHandler);
    }

    public Message(MessageType messageType, T content, MessageFailHandler messageFailHandler){
        this.messageType = messageType;
        this.content = content;
        this.failHandler = messageFailHandler;
    }

    public void addException(Exception e){
        this.exceptionList.add(e);
    }

    public void increaseFailCount(){
        failCount += 1;
    }

    public void takeFail(Exception e){
        failHandler.handleFail(this, e);
    }
}
