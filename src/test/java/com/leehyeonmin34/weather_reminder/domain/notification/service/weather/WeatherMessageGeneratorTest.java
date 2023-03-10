package com.leehyeonmin34.weather_reminder.domain.notification.service.weather;

import com.leehyeonmin34.weather_reminder.domain.notification.service.today_weather.TodayWeatherURLMessageGenerator;
import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.domain.user.domain.UserBuilder;
import com.leehyeonmin34.weather_reminder.domain.weather_info.builder.WeatherInfoListBuilder;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherInfoList;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
import com.leehyeonmin34.weather_reminder.domain.weather_info.service.WeatherInfoService;
import com.leehyeonmin34.weather_reminder.global.common.service.FutureHandler;
import com.leehyeonmin34.weather_reminder.global.test_config.ServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class WeatherMessageGeneratorTest extends ServiceTest {

    @InjectMocks
    WeatherNotiGenerator weatherNotiGenerator;

    @Mock
    HotMessageGenerator hotMessageGenerator;

    @Mock
    RainMessageGenerator rainMessageGenerator;

    @Mock
    ColdMessageGenerator coldMessageGenerator;

    @Spy
    List<WeatherMessageGenerator> msgGenerators = new ArrayList<>();

    @Spy
    FutureHandler futureHandler;

    @Mock
    WeatherInfoService weatherInfoService;

    @BeforeEach
    private void init(){
        msgGenerators.add(hotMessageGenerator);
        msgGenerators.add(coldMessageGenerator);
        msgGenerators.add(rainMessageGenerator);
    }

    @ParameterizedTest(name = "{index} : ?????? ????????? ?????? - {0}")
    @MethodSource("generateMessageTestConditions")
    public void generateMessageTest(String conditionName, boolean hotNotiOn, boolean coldNotiOn, boolean rainNotiOn, User user, String expected){
        // GIVEN
        String hotMsg = hotNotiOn ? "?????? ??? ??????" : "";
        String coldMsg = coldNotiOn ? "?????? ??? ??????" : "";
        String rainMsg = rainNotiOn ? "??? ??????" : "";
        String todayWeatherURL = "????????? ?????? URL";

        when(hotMessageGenerator.generate(any(User.class), any(WeatherInfoList.class))).thenReturn(hotMsg);
        when(coldMessageGenerator.generate(any(User.class), any(WeatherInfoList.class))).thenReturn(coldMsg);
        when(rainMessageGenerator.generate(any(User.class), any(WeatherInfoList.class))).thenReturn(rainMsg);
        when(weatherInfoService.getWeatherInfoListToday(any(WeatherRegion.class))).thenReturn(WeatherInfoListBuilder.build());

        // WHEN
        String result = weatherNotiGenerator.generateMessage(user);

        // THEN
        then(result).isEqualTo(expected);
    }

    private static Stream<Arguments> generateMessageTestConditions(){
        return Stream.of(
                Arguments.arguments("1??? ??????, 3??? ??????", true, true, true, UserBuilder.buildByOneRegion(), "?????? ?????? -----------\n\n?????? ??? ??????\n\n?????? ??? ??????\n\n??? ??????"),
                Arguments.arguments("1??? ??????, 2??? ??????", true, true, false, UserBuilder.buildByOneRegion(), "?????? ?????? -----------\n\n?????? ??? ??????\n\n?????? ??? ??????"),
                Arguments.arguments("1??? ??????, 1??? ??????", true, false, false, UserBuilder.buildByOneRegion(), "?????? ?????? -----------\n\n?????? ??? ??????"),
                Arguments.arguments("1??? ??????, 0??? ??????", false, false, false, UserBuilder.buildByOneRegion(), ""),
                Arguments.arguments("2??? ??????, 3??? ??????", true, true, true, UserBuilder.buildByTwoRegion(), "?????? ?????? -----------\n\n?????? ??? ??????\n\n?????? ??? ??????\n\n??? ??????\n\n?????? ?????? -----------\n\n?????? ??? ??????\n\n?????? ??? ??????\n\n??? ??????")
        );
    }

    @Test
    @DisplayName("?????? ????????? ?????? - ?????? ??? ????????? ????????? ?????? ??????")
    public void generateMessageWithExceptionTest(){
        // GIVEN
        when(hotMessageGenerator.generate(any(User.class), any(WeatherInfoList.class))).thenReturn("?????? ??? ??????");
        when(coldMessageGenerator.generate(any(User.class), any(WeatherInfoList.class))).thenThrow(new RuntimeException());
        when(rainMessageGenerator.generate(any(User.class), any(WeatherInfoList.class))).thenReturn("??? ??????");
        when(weatherInfoService.getWeatherInfoListToday(any(WeatherRegion.class))).thenReturn(WeatherInfoListBuilder.build());

        // WHEN
        String result = weatherNotiGenerator.generateMessage(UserBuilder.buildByOneRegion());

        // THEN
        then(result).isEqualTo("?????? ?????? -----------\n\n?????? ??? ??????\n\n??? ??????");
    }

}
