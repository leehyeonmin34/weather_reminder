package com.leehyeonmin34.weather_reminder.domain.session_info.repository;

import com.leehyeonmin34.weather_reminder.domain.session_info.domain.SessionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionInfoRepository extends JpaRepository<SessionInfo, String> {
}
