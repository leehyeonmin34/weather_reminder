package com.leehyeonmin34.weather_reminder.domain.notification.service.common;

import com.leehyeonmin34.weather_reminder.domain.notification.domain.Notification;
import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.global.message_q.model.Message;
import com.leehyeonmin34.weather_reminder.global.message_q.model.MessageType;
import com.leehyeonmin34.weather_reminder.global.message_q.service.MessageQ;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Slf4j
public class NotiGeneratorAndEnqueuer {

    final private List<NotiGenerator> notiGeneratorList;
    final private MessageQ messageQ;

    public Notification generateNotiAndEnqueue(User user){
        // 날씨, 먼지 메시지들을 생성하도록 실행해 futures에 담는다.
        List<CompletableFuture<String>> futures = notiGeneratorList.stream().map(
                gen -> CompletableFuture.supplyAsync(
                                () -> gen.generateMessage(user)
                        ).orTimeout(300L, TimeUnit.SECONDS) // Time 제한
                        .exceptionally(e -> "") // 예외가 발생하면 빈 문자열 리턴
        ).collect(Collectors.toList());

        // futures의 값들을 join
        String notiString = futures.stream().map(CompletableFuture::join)
                .filter(msg -> !msg.isEmpty())
                .collect(Collectors.joining("\n\n"));

        //  사용자에게 보내질 수 있게 메시지 큐에 추가해준다.
        Notification noti = new Notification.Builder(notiString, user.getId()).build();
        if(notiString != "") {
            messageQ.add(Message.of(noti));
            log.info("메시지 큐에 메시지가 추가되었습니다 - ", messageQ.toString());
        }
        return noti;
    };





}
