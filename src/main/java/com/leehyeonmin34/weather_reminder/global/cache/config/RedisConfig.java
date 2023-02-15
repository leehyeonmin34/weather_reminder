package com.leehyeonmin34.weather_reminder.global.cache.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class RedisConfig {

    public final String host;

    public final int port;

    private final RedisProperties redisProperties;

    public RedisConfig(final RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
        this.host = redisProperties.getHost();
        this.port = redisProperties.getPort();
    }


    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

//    @Bean
//    public RedisTemplate<?, ?> redisTemplate() {
//        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory());
//        return redisTemplate;
//    }

    @Bean(name = "cacheManager")
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
            .disableCachingNullValues() // null value 캐시안함
            .entryTtl(Duration.ofSeconds(CacheEnv.DEFAULT_EXPIRE_SEC)) // 캐시의 기본 유효시간 설정
            .computePrefixWith(CacheKeyPrefix.simple()) // prefix = "CacheName:"
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())); // redis 캐시 데이터 저장방식을 StringSeriallizer로 지정

        // 캐시키별 default 유효시간 설정
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put(CacheEnv.SESSION_INFO, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheEnv.SESSION_INFO_EXPIRE_SEC)));
        cacheConfigurations.put(CacheEnv.WEATHER_MSG_COLD, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheEnv.WEATHER_MSG_EXPIRE_SEC)));
        cacheConfigurations.put(CacheEnv.WEATHER_MSG_HOT, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheEnv.WEATHER_MSG_EXPIRE_SEC)));
        cacheConfigurations.put(CacheEnv.WEATHER_MSG_RAIN, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheEnv.WEATHER_MSG_EXPIRE_SEC)));
        cacheConfigurations.put(CacheEnv.TODAY_WEATHER_INFO_LIST, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheEnv.TODAY_WEATHER_INFO_LIST_EXPIRE_SEC)));

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory).cacheDefaults(configuration)
                .disableCreateOnMissingCache() // cacheManager.getCache(cacheName) 했을 때, 존재하지 않는 cacheName에 대한 Cache를 생성하지 않음
                .withInitialCacheConfigurations(cacheConfigurations).build();
    }
}
