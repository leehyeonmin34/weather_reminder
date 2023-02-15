package com.leehyeonmin34.weather_reminder.global.cache.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CacheableRepositoryBeans {

//    final private AccessRepository accessRepository;
//    final private CigaretteRepository cigaretteRepository;
//
//    final private CacheModule cacheModule;
//
//    @Bean
//    public CacheableRepository<Long, Access, AccessRepository> accessCacheableRepository(){
//        return new CacheableRepository<>(CacheEnv.ACCESS, accessRepository, Access::getId, cacheModule);
//    }
//
//    @Bean
//    public CacheableRepository<Long, Cigarette, CigaretteRepository> cigaretteCacheableRepository(){
//        return new CacheableRepository<>(CacheEnv.CIGARETTE, cigaretteRepository, Cigarette::getId, cacheModule);
//    }

}
