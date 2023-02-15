package com.leehyeonmin34.weather_reminder.domain.notification.service.weather;

import com.leehyeonmin34.weather_reminder.domain.notification.model.NotiContentType;
import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.domain.weather_info.domain.WeatherInfo;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherInfoList;
import com.leehyeonmin34.weather_reminder.domain.weather_info.service.WeatherApiTimeConverter;
import com.leehyeonmin34.weather_reminder.domain.weather_info.service.WeatherTempConverter;
import com.leehyeonmin34.weather_reminder.global.cache.config.CacheEnv;
import com.leehyeonmin34.weather_reminder.global.cache.service.CacheModule;
import com.leehyeonmin34.weather_reminder.global.common.service.TimeStringifier;
import lombok.RequiredArgsConstructor;
import org.hibernate.Cache;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RainMessageGenerator implements WeatherMessageGenerator{

    public static final NotiContentType notiContentType = NotiContentType.RAIN;

    private final CacheModule cacheModule;

    @Override
    public NotiContentType getNotiContentType(){ return notiContentType; }

    @Override
    public String generate(final User user, final WeatherInfoList weatherInfoList) {

        // 알림이 꺼져있다면 빈 문자열 반환
        if(!user.getRainNotiSetting().isOn())
            return "";

        // 캐시 조회 위한 키 (날짜). 같은 날짜와 조건을 가진 사용자라면 캐시에 있는 알림 메시지를 그대로 가져올 수 있음
        final String key = WeatherApiTimeConverter.serializeToDate(LocalDateTime.now());

        return cacheModule.getCacheOrLoad(CacheEnv.WEATHER_MSG_RAIN
                , key
                , (_key) -> generateForReal(user, weatherInfoList));

    }

    String generateForReal(final User user, final WeatherInfoList weatherInfoList) {

        // 비가 오는 시간대 구하기
        final List<WeatherInfo> satisfying = weatherInfoList.getRainWeatherInfoList().stream()
                .filter(item -> RainLevel.isRain(item.getValue()))
                .collect(Collectors.toList());

        // 조건을 만족하는 시간대가 없다면 빈 문자열 반환
        if(satisfying.size() == 0)
            return "";

        // 최대 강수량 추출
        final WeatherInfo peakTime = weatherInfoList.getRainWeatherInfoList().stream()
                .reduce((a, b) -> a.getValue() > b.getValue() ? a : b).get();

        // 조건을 만족하는 시간을 추출
        // ex) 오전 9시, 오후 4시, 5시
        final List<LocalDateTime> forcastTimeList = satisfying.stream().map(WeatherInfo::getFcstTime).collect(Collectors.toList());
        final String satisfyingCondigionString = TimeStringifier.stringifyByNoon(forcastTimeList);

        // 알림 메시지 생성하기
        return String.format("🌧️ 오늘 %s에 최대 %f%s의 %s가 예보되었어요. 우산을 잊지마세요! 흰 옷은 피하고 장화나 샌들을 신으면 더 좋겠죠?",
                satisfyingCondigionString,
                peakTime.getValue(),
                peakTime.getWeatherDataType().getUnit(),
                RainLevel.of(peakTime.getValue()).getDesc());
    }

}

