package com.leehyeonmin34.weather_reminder.domain.notification.service.today_weather;

import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TodayWeatherURLNotiStringGenerator {

    final private static String TODAY_WEAHTER_URL = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=%EC%98%A4%EB%8A%98+%EB%82%A0%EC%94%A8";

    public String generateMessage(final User user) {
        return "네이버 오늘 날씨 URL\n" + TODAY_WEAHTER_URL;
    }
}
