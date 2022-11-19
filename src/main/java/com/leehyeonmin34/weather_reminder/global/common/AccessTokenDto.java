package com.leehyeonmin34.weather_reminder.global.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccessTokenDto {
    @ApiModelProperty(value="액세스 토큰", example = "6e84ffb6-2a46-4be4-a9b0-0a8d4747b426", required = true)
    private String accessToken;
}
