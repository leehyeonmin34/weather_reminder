package com.leehyeonmin34.weather_reminder.domain.notification.service.common;

import com.leehyeonmin34.weather_reminder.domain.notification.domain.Notification;
import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class NotiGeneratorAndSender {

    final private List<NotiGenerator> notiGeneratorList;
    final private NotiQ notiQ;

    public Notification generateNotiAndSend(User user){
        // 날씨, 먼지 메시지들을 생성하도록 실행해 futures에 담는다.
        List<CompletableFuture<String>> futures = notiGeneratorList.stream().map(
                gen -> CompletableFuture.supplyAsync(
                                () -> gen.generateMessage(user)
                        ).orTimeout(300L, TimeUnit.SECONDS) // Time 제한
                        .exceptionally(e -> "") // 예외가 발생하면 빈 문자열 리턴
        ).collect(Collectors.toList());

        // futures의 값들을 join
        String notiString = futures.stream().map(CompletableFuture::join)
                .collect(Collectors.joining("\n\n"));

        //  사용자에게 보내질 수 있게 큐에 추가해준다.
        System.out.println(notiQ);
        Notification noti = new Notification.Builder(notiString, user.getId()).build();
        notiQ.add(noti);
        return noti;
    };



}
