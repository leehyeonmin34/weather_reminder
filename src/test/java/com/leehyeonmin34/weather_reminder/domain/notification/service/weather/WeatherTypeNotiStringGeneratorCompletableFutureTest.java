package com.leehyeonmin34.weather_reminder.domain.notification.service.weather;

import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.domain.user.domain.UserBuilder;
import com.leehyeonmin34.weather_reminder.domain.user.model.Region;
import com.leehyeonmin34.weather_reminder.domain.user.repository.UserRepository;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherInfoList;
import com.leehyeonmin34.weather_reminder.domain.weather_info.service.WeatherInfoService;
import com.leehyeonmin34.weather_reminder.global.cache.config.CacheEnv;
import com.leehyeonmin34.weather_reminder.global.cache.service.CacheModule;
import com.leehyeonmin34.weather_reminder.global.common.service.FutureHandler;
import com.leehyeonmin34.weather_reminder.global.error.exception.EntityNotFoundException;
import com.leehyeonmin34.weather_reminder.global.test_config.IntegrationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Ignore
public class WeatherTypeNotiStringGeneratorCompletableFutureTest extends IntegrationTest {

    @Autowired
    private WeatherNotiStringGenerator weatherNotiStringGenerator;

    @Autowired
    private FutureHandler futureHandler;

    @Autowired
    private WeatherInfoService weatherInfoService;

    @Autowired
    private List<WeatherTypeNotiStringGenerator> msgGenerators;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CacheModule cacheModule;

    @Test
    public void compareSyncAndAsync(){

//        User userBeforeSave = UserBuilder.buildByTwoRegion();
//        User user = userRepository.save(userBeforeSave);
        User user = userRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("user", 1L));

        for(int i = 0; i < 10; i++) {
            Long start1 = System.nanoTime();
            String msg1 = generateSynchronously(user);
            Long end1 = System.nanoTime();
            Long duration1 = end1 - start1;
            duration1 /= (long) Math.pow(10, 6);

//            evictCache();

            Long start2 = System.nanoTime();
            String msg2 = generateWithCompletableFuture(user);
            Long end2 = System.nanoTime();
            Long duration2 = end2 - start2;
            duration2 /= (long) Math.pow(10, 6);

//            evictCache();

            // 앞 3-5번은 워밍이라고 간주하고 그 뒤 일정한 값들로 평균을 냄
            // 캐시 miss : 1133ms, 790ms, 117ms / 59ms, 33ms, 33ms, 41ms, 46ms, 28ms, 58ms (avg 42.57ms)
            // 캐시 miss : 1235 49 51 61 51 59 54 77 24 19 (avg 42.57ms)
            // 캐시 hit : 951ms, 822ms, 707ms, 758ms / 20ms, 15ms, 17ms, 10ms, 11ms, 15ms, 11ms, 12ms, 21ms (avg 14.66ms)
            System.out.println(String.format("동기적 실행 : %d ms", duration1));

            // 캐시 miss : 1166ms, 420ms, 41ms, 35ms, 39ms / 22ms, 22ms, 19ms, 36ms, 21ms, 22ms (avg 23.66 ms)
            // 캐시 miss : 88 48 35 40 21 40 19 35 14 16 (avg 23.66 ms)
            // 캐시 hit : 33ms, 17ms, 21ms, 15ms, 17ms / 9ms, 7ms, 8ms, 6ms, 6ms, 7ms, 6ms, 5ms, 5ms (6.72 ms)
            System.out.println(String.format("비동기적 실행 : %d ms", duration2));
        }
    }

    private String generateSynchronously(User user){
        return user.getRegionList().stream()
                .map(region -> {
                    final WeatherInfoList weatherInfoList = weatherInfoService.getWeatherInfoListToday(region.getWeatherRegion());
                    String msgString = msgGenerators.stream().map(generator -> generator.generate(user, weatherInfoList))
                            .filter(item -> !item.isEmpty())
                            .collect(Collectors.joining("\n\n"));
                    return msgString.isBlank() ? "" : generateHeader(region) + msgString;
                })
                .filter(item -> !item.isEmpty())
                .collect(Collectors.joining("\n\n"));
    }

    private String generateWithCompletableFuture(User user){
        // 해당 유저의 알림 지역들에 대해 날씨 메시지 생성을 비동기적으로 호출.
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

    private void evictCache(){
        cacheModule.evictAll(CacheEnv.TODAY_WEATHER_INFO_LIST);
        cacheModule.evictAll(CacheEnv.WEATHER_MSG_HOT);
        cacheModule.evictAll(CacheEnv.WEATHER_MSG_RAIN);
        cacheModule.evictAll(CacheEnv.WEATHER_MSG_COLD);
    }




}
