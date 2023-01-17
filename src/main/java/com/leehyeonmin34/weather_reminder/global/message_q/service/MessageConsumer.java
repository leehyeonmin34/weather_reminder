package com.leehyeonmin34.weather_reminder.global.message_q.service;

import com.leehyeonmin34.weather_reminder.global.message_q.model.Message;
import com.leehyeonmin34.weather_reminder.global.message_q.service.handler.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
public class MessageConsumer {

    private final List<MessageHandler> messageHandlerList;
    private final MessageFailHandler failHandler;
    private final MessageQ messageQ;
    private final int threadPoolSize = 5;
    private boolean isTerminated = true;

    public MessageConsumer(MessageQ messageQ, List<MessageHandler> messageHandlerList, MessageFailHandler failHandler) {
        this.messageQ = messageQ;
        this.messageHandlerList = messageHandlerList;
        this.failHandler = failHandler;
    }

    @Scheduled(cron = "0/1 * * * * *")
    public void consume(){
        log.info("메시지큐에 메시지가 있는지 확인합니다.");
        if (!messageQ.isEmpty() && isTerminated)
            executeThreadPool();
    }

    private void executeThreadPool(){
        isTerminated = false;
        log.info(messageQ.size() + "개의 메시지가 있습니다.");
        log.info(threadPoolSize + "개의 스레드 풀을 생성해 메시지를 처리합니다.");
        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);

        IntStream.range(0, threadPoolSize).forEach(
                threadNum -> CompletableFuture.runAsync(this::process, executor));
        executor.shutdown();
    }

    private void process() {
        log.info("메시지 처리를 준비중입니다.");
        Message message = null;
        while ((message = messageQ.poll()) != null) {
            log.info("메시지를 처리합니다.");
            handleMessage(message);
        }
        isTerminated = true;
    }

    private void handleMessage(final Message message){
        messageHandlerList.forEach(messageHandler -> {
            try {
                messageHandler.handle(message);
            } catch(Exception e){
                failHandler.handleFail(message, e);
            }
        });
    }

}
