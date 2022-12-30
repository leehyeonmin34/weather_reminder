package com.leehyeonmin34.weather_reminder.domain.notification.service.weather;

import com.leehyeonmin34.weather_reminder.domain.notification.service.common.NotiGenerator;
import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.domain.weather_info.domain.WeatherInfo;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.Dong;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherInfoRepository;
import com.leehyeonmin34.weather_reminder.global.common.service.FutureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WeatherNotiGenerator implements NotiGenerator {

    private final List<WeatherMessageGenerator> msgGenerators;
    private final WeatherInfoRepository weatherInfoRepository;
    private final FutureHandler futureHandler;

    @Override
    public String generateMessage(User user){

        // TODO - dummy data
        WeatherInfo weatherInfo = new WeatherInfo("0","0", Dong.SEOUL, WeatherDataType.TEMP, WeatherDataType.TEMP.getUnit(), 0F);

        // 여러가지의 날씨 메시지 생성을 비동기적으로 호출.
        // 결과를 담은 future들을 리스트에 삽입.
        List<CompletableFuture<String>> msgFutures = msgGenerators.stream().map(
            generator -> CompletableFuture.supplyAsync(()
                    -> generator.generate(user, weatherInfo))
                    .orTimeout(60L, TimeUnit.SECONDS) // Time제한
                    .exceptionally(e -> "error") // 예외가 발생하면 빈 문자열 리턴
        ).collect(Collectors.toUnmodifiableList());

        // future 리스트에 담긴 값들을 읽어 문자열로 조합
        return futureHandler.joinFutureList(msgFutures).stream().collect(Collectors.joining("\n\n"));
    }






}

