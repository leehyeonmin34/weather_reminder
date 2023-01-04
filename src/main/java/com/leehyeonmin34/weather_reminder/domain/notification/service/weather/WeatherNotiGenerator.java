package com.leehyeonmin34.weather_reminder.domain.notification.service.weather;

import com.leehyeonmin34.weather_reminder.domain.notification.service.common.NotiGenerator;
import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.domain.user.model.Region;
import com.leehyeonmin34.weather_reminder.domain.weather_info.domain.WeatherInfo;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherInfoList;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherInfoRepository;
import com.leehyeonmin34.weather_reminder.domain.weather_info.service.WeatherInfoService;
import com.leehyeonmin34.weather_reminder.global.common.service.FutureHandler;
import com.leehyeonmin34.weather_reminder.global.common.service.StringJoiner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class WeatherNotiGenerator implements NotiGenerator {

    private final List<WeatherMessageGenerator> msgGenerators;
    private final WeatherInfoService weatherInfoService;
    private final FutureHandler futureHandler;

    @Override
    public String generateMessage(final User user){
        // 해당 유저의 알림 지역들에 대해 날씨 메시지 생성을 비동기적으로 호출.
        // 결과를 담은 future들을 리스트에 삽입.
        final List<CompletableFuture<String>> msgFutures = user.getRegionList().stream().map(
                region -> CompletableFuture.supplyAsync(()
                                -> generateMessageByRegion(user, region))
                        .orTimeout(60L, TimeUnit.SECONDS) // Time제한
                        .exceptionally(e -> "") // TODO - 예외 발생 시 사용자에겐 메시지가 보내지지 않지만 개발자에겐 리포트 되어야 할 것
        ).collect(Collectors.toUnmodifiableList());

        // future 리스트에 담긴 값들을 읽어 문자열로 조합
        return futureHandler.joinFutureList(msgFutures).stream()
                .filter(item -> !item.isEmpty())
                .collect(Collectors.joining("\n\n"));
    }

    private String generateMessageByRegion(final User user, final Region region){

        // 해당 지역의 날씨정보들을 불러옴
        final WeatherInfoList weatherInfoList = weatherInfoService.getWeatherInfoListToday(region.getWeatherRegion());

        // 여러가지의 날씨 메시지 생성을 비동기적으로 호출.
        // 결과를 담은 future들을 리스트에 삽입.
        final List<CompletableFuture<String>> msgFutures = msgGenerators.stream().map(
                generator -> CompletableFuture.supplyAsync(()
                                -> generator.generate(user, weatherInfoList))
                        .orTimeout(60L, TimeUnit.SECONDS) // Time제한
                        .exceptionally(e -> "")
        ).collect(Collectors.toUnmodifiableList());

        String header = String.format("%s 날씨 -----------\n\n", region.getName());
        // TODO - footer로 해당 지역의 날씨 URL 삽입

        // future 리스트에 담긴 값들을 읽어 문자열로 조합
        return header + futureHandler.joinFutureList(msgFutures).stream()
                .filter(item -> !item.isEmpty())
                .collect(Collectors.joining("\n\n"));
    }






}

