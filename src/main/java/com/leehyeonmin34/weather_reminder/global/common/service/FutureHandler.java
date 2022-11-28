package com.leehyeonmin34.weather_reminder.global.common.service;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class FutureHandler {

    public <T> List<T> joinFutureList(List<CompletableFuture<T>> futureList){
        //future 리스트를 순회하며 join
        return futureList.stream().map(CompletableFuture::join).collect(Collectors.toUnmodifiableList());
    }


}
