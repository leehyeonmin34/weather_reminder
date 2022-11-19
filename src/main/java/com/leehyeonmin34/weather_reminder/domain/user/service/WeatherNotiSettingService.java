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

    final UserRepository userRepository;

    final NotiSettingUtils notiSettingUtils;

    public void turnOffNoti(Long userId, String notiTypeCode){
        NotiSetting notiSetting = notiSettingUtils.findNotiSetting(userId, notiTypeCode);
        notiSetting.turnOff();
    }

    public void updateRainNoti(Long userId, Byte conditionTime) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.getSnowNotiSetting().changeConditionTime(conditionTime);
    }

    public void updateSnowNoti(Long userId, Byte conditionTime) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.getSnowNotiSetting().changeConditionTime(conditionTime);
    }

    public void updateHotNoti(Long userId, Byte conditionCelcius) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.getHotNotiSetting().changeConditionCelcius(conditionCelcius);
    }

    public void updateColdNoti(Long userId, Byte conditionCelcius) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.getColdNotiSetting().changeConditionCelcius(conditionCelcius);
    }



}
