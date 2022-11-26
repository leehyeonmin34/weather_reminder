package com.leehyeonmin34.weather_reminder.domain.notification.service.dust;

import com.leehyeonmin34.weather_reminder.domain.dust_info.domain.DustInfo;
import com.leehyeonmin34.weather_reminder.domain.dust_info.repository.DustInfoRepository;
import com.leehyeonmin34.weather_reminder.domain.notification.service.common.NotiGenerator;
import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.global.common.service.FutureHandler;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DustNotiGenerator implements NotiGenerator {

    private final List<DustMessageGenerator> msgGenerators;
    private final DustInfoRepository dustInfoRepository;
    private final FutureHandler futureHandler;

    @Override
    public String generateMessage(User user) {
        DustInfo dustInfo = new DustInfo();

        // 여러가지의 먼지 메시지 생성을 비동기적으로 호출.
        // 결과를 담은 future들을 리스트에 삽입.
        List<CompletableFuture<String>> msgFutures = msgGenerators.stream().map(
                generator -> CompletableFuture.supplyAsync(()
                                -> generator.generate(user, dustInfo))
                        .orTimeout(60L, TimeUnit.SECONDS) // Time제한
                        .exceptionally(e -> "") // 예외가 발생하면 빈 문자열 리턴
        ).collect(Collectors.toUnmodifiableList());

        // future 리스트에 담긴 값들을 읽어 문자열로 조합
        return futureHandler.joinFutureList(msgFutures).stream().collect(Collectors.joining("\n\n"));
    }



}
