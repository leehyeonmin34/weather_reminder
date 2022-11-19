package com.leehyeonmin34.weather_reminder.domain.user.dto;

import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class UserGetResponse implements Serializable {
    @ApiModelProperty(value="사용자 Primary Key 인덱스")
    private Long id;

    @ApiModelProperty(value="가입시점")
    private LocalDateTime createdAt;

    @ApiModelProperty(value="수정시점")
    private LocalDateTime updatedAt;


}