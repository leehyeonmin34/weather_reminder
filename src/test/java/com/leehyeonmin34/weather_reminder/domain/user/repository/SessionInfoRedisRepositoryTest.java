package com.leehyeonmin34.weather_reminder.domain.user.repository;

import com.leehyeonmin34.weather_reminder.domain.session_info.domain.SessionInfoRedis;
import com.leehyeonmin34.weather_reminder.domain.session_info.repository.SessionInfoRedisRepository;
import com.leehyeonmin34.weather_reminder.domain.user.domain.SessionInfoRedisBuilder;
import com.leehyeonmin34.weather_reminder.global.parent.RedisRepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import static org.assertj.core.api.BDDAssertions.then;

public class SessionInfoRedisRepositoryTest extends RedisRepositoryTest {

    @Autowired
    private SessionInfoRedisRepository redisRepository;

    private SessionInfoRedis sessionInfoRedis;

    @BeforeEach
    private void setUp(){
        sessionInfoRedis = SessionInfoRedisBuilder.build();
    }

    @Test
    public void sessionRedisSaveTest() {

        // WHEN
        SessionInfoRedis result = redisRepository.save(sessionInfoRedis);

        // THEN
        then(result.getAccessToken()).isEqualTo(sessionInfoRedis.getAccessToken());
        then(result.getUserAgent()).isEqualTo(sessionInfoRedis.getUserAgent());
        then(result.getUserId()).isEqualTo(sessionInfoRedis.getUserId());
        then(result.getExpirationTime()).isEqualTo(sessionInfoRedis.getExpirationTime());
        then(result.getAccessToken()).isEqualTo(sessionInfoRedis.getAccessToken());
    }

    @Test
    public void sessionRedisFindTest(){
        // WHEN
        SessionInfoRedis saved = redisRepository.save(sessionInfoRedis);
        SessionInfoRedis result = redisRepository.findById(saved.getAccessToken()).get();

        // THEN
        then(result.getAccessToken()).isEqualTo(sessionInfoRedis.getAccessToken());
        then(result.getUserAgent()).isEqualTo(sessionInfoRedis.getUserAgent());
        then(result.getUserId()).isEqualTo(sessionInfoRedis.getUserId());
        then(result.getExpirationTime()).isEqualTo(sessionInfoRedis.getExpirationTime());
        then(result.getAccessToken()).isEqualTo(sessionInfoRedis.getAccessToken());
    }


}
