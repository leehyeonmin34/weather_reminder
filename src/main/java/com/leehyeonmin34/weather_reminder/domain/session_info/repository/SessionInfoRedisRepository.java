package com.leehyeonmin34.weather_reminder.domain.session_info.repository;


import com.leehyeonmin34.weather_reminder.domain.session_info.domain.SessionInfoRedis;
import org.springframework.data.repository.CrudRepository;

public interface SessionInfoRedisRepository extends CrudRepository<SessionInfoRedis, String> {
}
