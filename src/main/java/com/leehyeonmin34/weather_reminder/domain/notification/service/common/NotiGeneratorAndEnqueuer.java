package com.leehyeonmin34.weather_reminder.domain.notification.service.common;

import com.leehyeonmin34.weather_reminder.domain.notification.domain.Notification;
import com.leehyeonmin34.weather_reminder.domain.notification.service.today_weather.TodayWeatherURLMessageGenerator;
import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.global.message_q.model.Message;
import com.leehyeonmin34.weather_reminder.global.message_q.service.MessageFactory;
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
    final private MessageFactory messageFactory;
    final private TodayWeatherURLMessageGenerator todayWeatherURLMessageGenerator;

    public Notification generateNotiAndEnqueue(final User user){
        // 날씨, 먼지 메시지들을 생성하도록 실행해 futures에 담는다.
        final List<CompletableFuture<String>> futures = notiGeneratorList.stream().map(
                gen -> CompletableFuture.supplyAsync(
                                () -> gen.generateMessage(user)
                        ).orTimeout(300L, TimeUnit.SECONDS) // Time 제한
                        .exceptionally(e -> "") // 예외가 발생하면 빈 문자열 리턴
        ).collect(Collectors.toList());

        // futures의 값들을 join
        final String notiString = futures.stream().map(CompletableFuture::join)
                .filter(msg -> !msg.isEmpty())
                .collect(Collectors.joining("\n\n"));

        // 전달할 내용이 있을 경우, 오늘의 날씨 URL을 포함해
        // 사용자에게 보내질 수 있게 메시지 큐에 추가해준다.
        if(!notiString.isEmpty()) {
            final String finalNotiString = notiString + todayWeatherURLMessageGenerator.generateMessage(user);
            Notification noti = new Notification.Builder(finalNotiString, user.getId()).build();
            messageQ.add(messageFactory.createNotiMessage(noti));
            return noti;
        }

        return null;
    };





}
