package com.leehyeonmin34.weather_reminder.global.message_q.service;

import com.leehyeonmin34.weather_reminder.global.message_q.model.Message;
import com.leehyeonmin34.weather_reminder.global.message_q.service.handler.MessageHandler;
import com.leehyeonmin34.weather_reminder.global.message_q.service.handler.NotiMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class MessageConsumer {

    private final List<MessageHandler> messageHandlerList;
    private final MessageQ messageQ;

    public MessageConsumer(MessageQ messageQ, List<MessageHandler> messageHandlerList) {
        this.messageQ = messageQ;
        this.messageHandlerList = messageHandlerList;
        listen();
    }

//    @Scheduled(cron = "0/1 * * * * *")
    private void listen(){
        log.info("메시지큐에 메시지가 있는지 확인합니다.");
        if (!messageQ.isEmpty())
            executeThreadPool();
    }

//    private void listen(){
//        new Thread() {
//
//            { setDaemon(true); }
//
//            @Override
//            public void run() {
//                while(true) {
//                    log.info("메시지큐에 메시지가 있는지 확인합니다.");
//                    process();
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }.start();
//    }

    private void executeThreadPool(){
        ExecutorService service = Executors.newFixedThreadPool(5);
        service.submit(() -> process());
        service.shutdown();
    }

    private void process() {
        Message message = null;
        while ((message = messageQ.poll()) != null) {
            handleMessage(message);
        }
    }

    private void handleMessage(final Message message){
        messageHandlerList.forEach(messageHandler -> messageHandler.handle(message));
    }

}
