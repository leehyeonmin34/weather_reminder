package com.leehyeonmin34.weather_reminder.domain.user.service;

import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.domain.user.model.NotiSetting;
import com.leehyeonmin34.weather_reminder.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class WeatherNotiSettingService {

    private final UserRepository userRepository;

    private final NotiSettingUtils notiSettingUtils;

    public void turnOffNoti(final Long userId, final String notiTypeCode){
        final NotiSetting notiSetting = notiSettingUtils.findNotiSetting(userId, notiTypeCode);
        notiSetting.turnOff();
    }

    public void updateRainNoti(final Long userId, final Byte conditionTime) {
        final User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.getSnowNotiSetting().changeConditionTime(conditionTime);
    }

    public void updateSnowNoti(final Long userId, final Byte conditionTime) {
        final User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.getSnowNotiSetting().changeConditionTime(conditionTime);
    }

    public void updateHotNoti(final Long userId, final Byte conditionCelcius) {
        final User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.getHotNotiSetting().changeConditionCelcius(conditionCelcius);
    }

    public void updateColdNoti(final Long userId, final Byte conditionCelcius) {
        final User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.getColdNotiSetting().changeConditionCelcius(conditionCelcius);
    }



}
