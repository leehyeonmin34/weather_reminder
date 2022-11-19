package com.leehyeonmin34.weather_reminder.domain.session_info.service;

import com.leehyeonmin34.weather_reminder.domain.session_info.domain.SessionInfo;
import com.leehyeonmin34.weather_reminder.domain.session_info.repository.SessionInfoRepository;
import com.leehyeonmin34.weather_reminder.domain.session_info.exception.SessionInfoNotExistsException;
import com.leehyeonmin34.weather_reminder.global.config.CacheEnv;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Qualifier("SessionServiceImplCacheAnnotion")
@RequiredArgsConstructor
public class SessionServiceImplCacheAnnotation implements SessionService {

    final private SessionInfoRepository sessionInfoRepository;

    final private SessionInfoCacheService sessionInfoCacheService;

    @Override
    public boolean existsByToken(String accessToken){
        try {
            getSessionElseThrow(accessToken);
            return true;
        } catch (SessionInfoNotExistsException e) {
            return false;
        }
    }

    @Override
    @Cacheable(value = CacheEnv.SESSION_INFO, key = "#accessToken", unless = "#result == null")
    public SessionInfo getSessionElseThrow(String accessToken) {

        // 캐시에 없으면 DB 조회
        SessionInfo sessionInfo = sessionInfoRepository.findById(accessToken)
                .orElseThrow(SessionInfoNotExistsException::new);

        // 만료되면 삭제 후 예외 발생
        removeIfExpired(sessionInfo);

        // 리턴
        return sessionInfo;
    }
        private void removeIfExpired(SessionInfo sessionInfo){
        if(sessionInfo.isExpired()){
            removeSession(sessionInfo.getAccessToken());
            throw new SessionInfoNotExistsException();
        }
    }

    @Override
    public SessionInfo registerSession(UserGetResponse user, String userAgent) {
        String accessToken = UUID.randomUUID().toString();

        SessionInfo sessionInfo = new SessionInfo.Builder(accessToken, user.getId())
                .userAgent(userAgent)
                .expirationTime(LocalDateTime.now().plusDays(1))
                .build();
        SessionInfo saved = sessionInfoRepository.save(sessionInfo);
        sessionInfoCacheService.saveSessionInfoCache(accessToken, saved);

        return saved;
    }


    @Override
    @CacheEvict(value = CacheEnv.SESSION_INFO, key = "#accessToken")
    public void removeSession(String accessToken) {
        if(sessionInfoRepository.existsById(accessToken))
            sessionInfoRepository.deleteById(accessToken);
    }
}
