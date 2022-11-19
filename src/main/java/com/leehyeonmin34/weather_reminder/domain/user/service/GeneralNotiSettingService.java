package com.leehyeonmin34.weather_reminder.domain.user.service;

import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.domain.user.model.Region;
import com.leehyeonmin34.weather_reminder.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class GeneralNotiSettingService {

    final UserRepository userRepository;

    public void addRegion(Long userId, String regionCode){
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.addRegion(regionCode);
    }

    public void removeRegion(Long userId, String regionCode){
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.removeRegion(regionCode);
    }

    public void setRemindTime(Long userId, Byte remindTime){
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.setRemindAt(remindTime);
    }

    public void pauseReminding(Long userId, Long pauseDays){
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.pauseRemindingForNDays(pauseDays);
    }

    public void restartReminding(Long userId){
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.restartReminding();
    }

}
