package com.leehyeonmin34.weather_reminder.domain.user.service;

import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.domain.user.repository.UserRepository;
import com.leehyeonmin34.weather_reminder.global.common.model.DayTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class GeneralNotiSettingService {

    private final UserRepository userRepository;

    public void addRegion(final Long userId, final String regionCode){
        final User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.addRegion(regionCode);
    }

    public void removeRegion(final Long userId, final String regionCode){
        final User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.removeRegion(regionCode);
    }

    public void setRemindTime(final Long userId, final DayTime remindTime){
        final User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.setNotiTime(remindTime);
    }

    public void pauseReminding(final Long userId, final Long pauseDays){
        final User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.pauseRemindingForNDays(pauseDays);
    }

    public void restartReminding(final Long userId){
        final User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.restartReminding();
    }

}
