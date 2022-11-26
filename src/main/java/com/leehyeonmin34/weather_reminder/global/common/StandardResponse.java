package com.leehyeonmin34.weather_reminder.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.leehyeonmin34.weather_reminder.global.error.ErrorResponse;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StandardResponse<T> {

    @NotNull
    private int status;

    @Nullable
    private ErrorResponse errorResponse;

    @Nullable
    private T data;

}
