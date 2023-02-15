package com.leehyeonmin34.weather_reminder.global.message_q.service;

import com.leehyeonmin34.weather_reminder.domain.notification.domain.Notification;
import com.leehyeonmin34.weather_reminder.global.message_q.model.Message;
import com.leehyeonmin34.weather_reminder.global.message_q.service.handler.MessageHandler;
import com.leehyeonmin34.weather_reminder.global.message_q.service.handler.NotiMessageHandler;
import com.leehyeonmin34.weather_reminder.global.test_config.ServiceTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
public class MessageQCustomerTest extends ServiceTest {

    @Autowired
    MessageConsumer messageConsumer;

    @Autowired
    MessageFactory messageFactory;

    @SpyBean
    MessageQ messageQ;

    @MockBean
    private List<MessageHandler> messageHandlerList;

    @MockBean
    private NotiMessageHandler notiMessageHandler;

    @BeforeEach
    private void init(){
        messageHandlerList.add(notiMessageHandler);
    }

    @Test
    @DisplayName("여러 스레드에서 동시에 생성된 10000개 메시지 처리 ")
    public void consumeTest() throws InterruptedException {

        // GIVEN
        int msgNum = 10000;

        // WHEN
        IntStream.range(0, msgNum)
                .parallel()
                .forEach(i -> messageQ.add(createMessage("msg" + i)));

        // 메시지 큐 내의 메시지를 consumer가 처리할때까지 대기
        Thread.sleep(1000);
        while (!messageQ.isEmpty()) { }
        Thread.sleep(1000);

        // THEN
        then(messageQ.isEmpty()).isTrue();
        verify(notiMessageHandler, times(msgNum)).handle(any(Message.class));
    }


    // 메시지가 계속 예외를 발생시킬 때, 메시지에 지정된 tolerance 횟수까지 handling 시도(큐에 다시 집어 넣음)
    @Test
    @DisplayName("여러 스레드에서 동시에 생성된 10개 메시지 처리 중 예외 발생")
    public void consumeTestWithFail() throws InterruptedException {

        // GIVEN
        int msgNum = 10;
        int tolerance = 3;

        // WHEN
        lenient().doThrow(new RuntimeException()).when(notiMessageHandler).handle(any(Message.class));
        IntStream.range(0, msgNum)
            .parallel()
            .forEach(i -> messageQ.add(createMessage("msg" + i)));

        // 메시지 큐 내의 메시지를 consumer가 처리할때까지 대기
        Thread.sleep(1000);
        while (!messageQ.isEmpty()){ }
        Thread.sleep(1000);

        // THEN
        then(messageQ.isEmpty()).isTrue();
        verify(notiMessageHandler, times(msgNum * tolerance )).handle(any(Message.class));
    }


    private Message createMessage(String msg) {
        return messageFactory.createNotiMessage(new Notification.Builder(msg, 0L).build());
    }

}
