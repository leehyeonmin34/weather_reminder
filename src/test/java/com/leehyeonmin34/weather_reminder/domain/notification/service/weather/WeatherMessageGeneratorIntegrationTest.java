package com.leehyeonmin34.weather_reminder.domain.notification.service.weather;

import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.domain.user.domain.UserBuilder;
import com.leehyeonmin34.weather_reminder.domain.weather_info.builder.WeatherInfoListBuilder;
import com.leehyeonmin34.weather_reminder.domain.weather_info.domain.WeatherInfo;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherInfoRepository;
import com.leehyeonmin34.weather_reminder.global.test_config.IntegrationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.when;

@Slf4j
public class WeatherMessageGeneratorIntegrationTest extends IntegrationTest {

    @Autowired
    WeatherNotiGenerator weatherNotiGenerator;

    @MockBean
    WeatherInfoRepository weatherInfoRepository;

    @BeforeEach
    private void init(){
        setupRepositoryMocking();
    }

    private void setupRepositoryMocking(){
        // 서울 날씨 정보 셋업
        List<WeatherInfo> seoulWeatherInfoList = new ArrayList<>();
        List<WeatherInfo> seoulTemp = WeatherInfoListBuilder.build(LocalDateTime.now(), WeatherRegion.SEOUL, WeatherDataType.TEMP, 0).getTempWeatherInfoList();
        List<WeatherInfo> seoulRain = WeatherInfoListBuilder.build(LocalDateTime.now(), WeatherRegion.SEOUL, WeatherDataType.RAIN, 0).getRainWeatherInfoList();
        seoulWeatherInfoList.addAll(seoulTemp);
        seoulWeatherInfoList.addAll(seoulRain);

        // 부산 날씨 정보 셋업
        List<WeatherInfo> busanWeatherInfoList = new ArrayList<>();
        List<WeatherInfo> busanTemp = WeatherInfoListBuilder.build(LocalDateTime.now(), WeatherRegion.BUSAN, WeatherDataType.TEMP, 0).getTempWeatherInfoList();
        List<WeatherInfo> busanRain = WeatherInfoListBuilder.build(LocalDateTime.now(), WeatherRegion.BUSAN, WeatherDataType.RAIN, 0).getRainWeatherInfoList();
        busanWeatherInfoList.addAll(busanTemp);
        busanWeatherInfoList.addAll(busanRain);

        // Repository 모킹
        when(weatherInfoRepository.findAllTodayByWeatherRegion(WeatherRegion.SEOUL)).thenReturn(seoulWeatherInfoList);
        when(weatherInfoRepository.findAllTodayByWeatherRegion(WeatherRegion.BUSAN)).thenReturn(busanWeatherInfoList);
    }


    @Test
    @DisplayName("날씨메시지 생성 - 준 통합테스트(DB 통신 제외)")
    public void generateMessageTest(){
        // GIVEN
        User user = UserBuilder.buildByTwoRegion();

        // WHEN
        String result = weatherNotiGenerator.generateMessage(user);

        // THEN - 콘솔에 출력되는 메시지가 전반적으로 양식에 맞는지만 확인 (메시지 양식이 계속 변할 것 같기 때문)
        // 세부적인 테스트는 해당 클래스 테스트에서!
        log.info(result);
        then(result).isNotEmpty();
    }


}
