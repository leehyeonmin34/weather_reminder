package com.leehyeonmin34.weather_reminder.global.message_q.service;

import com.leehyeonmin34.weather_reminder.global.message_q.model.Message;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.SynchronousQueue;

@Component
@ToString
@Slf4j
public class MessageQ {

    private final Queue<Message> queue = new ConcurrentLinkedQueue<>();

    public void add(Message noti){
        queue.add(noti);
    }

    public Message poll(){
        return queue.poll();
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }

    public int size(){ return queue.size(); }

}
