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
<<<<<<< HEAD
    public String generateMessage(final User user){
        // 해당 유저의 알림 지역들에 대해 날씨 메시지 생성을 비동기적으로 호출.
=======
    public String generateMessage(User user){

        // TODO - dummy data
        WeatherInfo weatherInfo = new WeatherInfo("0","0", WeatherRegion.SEOUL, WeatherDataType.TEMP, WeatherDataType.TEMP.getUnit(), 0F);

        // 여러가지의 날씨 메시지 생성을 비동기적으로 호출.
>>>>>>> 589cb3d (날씨 정보 조회 배치 2)
        // 결과를 담은 future들을 리스트에 삽입.
        final List<CompletableFuture<String>> msgFutures = user.getRegionList().stream().map(
                region -> CompletableFuture.supplyAsync(()
                                -> generateMessageByRegion(user, region))
                        .orTimeout(60L, TimeUnit.SECONDS) // Time제한
                        .exceptionally(this::handleException)
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
                        .exceptionally(this::handleException)
        ).collect(Collectors.toUnmodifiableList());

        // future 리스트에 담긴 값들을 읽어 문자열로 조합
        final String msgString = futureHandler.joinFutureList(msgFutures).stream()
                .filter(item -> !item.isEmpty())
                .collect(Collectors.joining("\n\n"));

        return msgString.isBlank() ? "" : generateHeader(region) + msgString;
    }

    private String generateHeader(final Region region){
        return String.format("%s 날씨 -----------\n\n", region.getName());
    }

    private String handleException(Throwable e){
        e.printStackTrace();
        return "";
    }






}

