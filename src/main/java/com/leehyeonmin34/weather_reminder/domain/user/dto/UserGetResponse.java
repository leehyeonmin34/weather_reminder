package com.leehyeonmin34.weather_reminder.domain.user.dto;

import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.domain.user.model.*;
import com.leehyeonmin34.weather_reminder.global.common.model.DayTime;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class UserGetResponse implements Serializable {
    @ApiModelProperty(value="사용자 Primary Key 인덱스")
    private Long id;

    @ApiModelProperty(value="가입시점")
    private LocalDateTime createdAt;

    @ApiModelProperty(value="수정시점")
    private LocalDateTime updatedAt;

    private List<Region> regionList =  new ArrayList<>();

    private HotNotiSetting hotNotiSetting;

    private RainNotiSetting rainNotiSetting;

    private ColdNotiSetting coldNotiSetting;

    private SnowNotiSetting snowNotiSetting;

    private DayTime notiTime;

    private LocalDateTime pauseUntil;

    public UserGetResponse(User user){
        this.id = user.getId();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.regionList = user.getRegionList();
        this.hotNotiSetting = user.getHotNotiSetting();
        this.coldNotiSetting = user.getColdNotiSetting();
        this.rainNotiSetting = user.getRainNotiSetting();
        this.snowNotiSetting = user.getSnowNotiSetting();
        this.notiTime = user.getNotiTime();
        this.pauseUntil = user.getPauseUntil();
    }

    public User toEntity(UserGetResponse dto){
        return new User.Builder()
                .id(dto.getId())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .hotNotiSetting(dto.getHotNotiSetting())
                .coldNotiSetting(dto.getColdNotiSetting())
                .rainNotiSetting(dto.getRainNotiSetting())
                .snowNotiSetting(dto.getSnowNotiSetting())
                .regionList(dto.getRegionList())
                .notiTime(dto.getNotiTime())
                .pauseUntil(dto.getPauseUntil())
                .build();
    }




}