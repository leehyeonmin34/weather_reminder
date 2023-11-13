package com.leehyeonmin34.weather_reminder.domain.notification.service.common;

import com.leehyeonmin34.weather_reminder.domain.notification.domain.Notification;
import com.leehyeonmin34.weather_reminder.domain.notification.service.today_weather.TodayWeatherURLNotiStringGenerator;
import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotiGenerator {

    final private List<NotiStringGenerator> notiStringGenerators;
    final private TodayWeatherURLNotiStringGenerator todayWeatherURLNotiStringGenerator;

    public Optional<Notification> generate(final User user){
        final List<CompletableFuture<String>> futures = getMessageFutures(user);
        final String notiString = getJoinedMessage(futures);
        if(notiString.isEmpty()) return Optional.empty();

        final String finalNotiString = notiString + todayWeatherURLNotiStringGenerator.generateMessage(user);
        return Optional.of(new Notification.Builder(finalNotiString, user.getId()).build());
    };

    private List<CompletableFuture<String>> getMessageFutures(User user){
        return notiStringGenerators.stream().map(
                gen -> CompletableFuture.supplyAsync(
                                () -> gen.generateMessage(user)
                        ).orTimeout(300L, TimeUnit.SECONDS) // Time 제한
                        .exceptionally(e -> "") // 예외가 발생하면 빈 문자열 리턴
        ).collect(Collectors.toList());
    }

    private String getJoinedMessage(List<CompletableFuture<String>> futures){
        return futures.stream().map(CompletableFuture::join)
                .filter(msg -> !msg.isEmpty())
                .collect(Collectors.joining("\n\n"));
    }

}
