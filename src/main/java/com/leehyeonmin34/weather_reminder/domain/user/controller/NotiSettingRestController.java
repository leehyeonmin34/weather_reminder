package com.leehyeonmin34.weather_reminder.domain.user.controller;

import com.leehyeonmin34.weather_reminder.domain.user.service.WeatherNotiSettingService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class NotiSettingRestController {

    final WeatherNotiSettingService notiSettingService;

    @PostMapping("/{id}/cold")
    public ResponseEntity<Object> setColdNoti(@PathVariable String id, @NonNull String conditionCelcius){
        notiSettingService.updateColdNoti(Long.valueOf(id), Byte.valueOf(conditionCelcius));
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/{id}/hot")
    public ResponseEntity<Object> setHotNoti(@PathVariable String id,  @NonNull String conditionCelcius){
        notiSettingService.updateHotNoti(Long.valueOf(id), Byte.valueOf(conditionCelcius));
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/{id}/rain")
    public ResponseEntity<Object> setRainNoti(@PathVariable String id,  @NonNull String conditionTime){
        notiSettingService.updateRainNoti(Long.valueOf(id), Byte.valueOf(conditionTime));
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/{id}/snow")
    public ResponseEntity<Object> setSnowNoti(@PathVariable String id,  @NonNull String conditionTime){
        notiSettingService.updateSnowNoti(Long.valueOf(id), Byte.valueOf(conditionTime));
        return ResponseEntity.ok().body(null);
    }



}
