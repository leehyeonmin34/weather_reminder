package com.leehyeonmin34.weather_reminder.domain.notification.service.common;

import com.leehyeonmin34.weather_reminder.domain.notification.domain.Notification;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Component
public class NotiQ {

    private final Queue<Notification> queue = new LinkedList<>();

    public void add(final Notification noti){
        queue.add(noti);
    }

    public Notification poll(){
        return queue.poll();
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }

}
