//package com.leehyeonmin34.weather_reminder.domain.session_info.service;
//
//
//import com.leehyeonmin34.weather_reminder.domain.session_info.domain.SessionInfo;
//import com.leehyeonmin34.weather_reminder.domain.session_info.exception.SessionInfoNotExistsException;
//import com.leehyeonmin34.weather_reminder.domain.session_info.repository.SessionInfoRepository;
//import com.leehyeonmin34.weather_reminder.global.common.service.CacheModule;
//import com.leehyeonmin34.weather_reminder.global.cache.config.CacheEnv;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Primary;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//
//@Primary
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class SessionServiceImplCacheModule implements SessionService {
//
//    final private SessionInfoRepository sessionInfoRepository;
//    final private CacheModule cacheModule;
//
//    @Override
//    public boolean existsByToken(String accessToken){
//        try {
//            getSessionElseThrow(accessToken);
//            return true;
//        } catch (SessionInfoNotExistsException e) {
//            return false;
//        }
//    }
//
//    @Override
//    public SessionInfo getSessionElseThrow(String accessToken){
//
//        // 캐시, DB 조회
//        SessionInfo sessionInfo = cacheModule.getCacheOrLoad(CacheEnv.SESSION_INFO,
//                accessToken,
//                key -> sessionInfoRepository.findById(key)
//                        .orElseThrow(SessionInfoNotExistsException::new)
//        );
//
//
//        // 만료되면 삭제 후 예외 발생
//        // TODO - 만료된 세션정보는 특정 스레드가 주기적으로 처리하면 좋을 것 같다.
//        removeIfExpired(sessionInfo);
//
//        // 리턴
//          return sessionInfo;
//    }
//
//
//    private void removeIfExpired(SessionInfo sessionInfo){
//        try {
//            sessionInfo.checkExpiration();
//        } catch (AccessedExpiredSessionTokenException e){
//            cacheModule.deleteThrough(CacheEnv.SESSION_INFO, sessionInfo.getAccessToken(), sessionInfoRepository::deleteById);
//            throw new SessionInfoNotExistsException();
//        }
//    }
//
//    // 세션 키 등록
//    @Override
//    public SessionInfo registerSession(UserGetResponse user, String userAgent) {
//
//        // 세션정보 생성
//        String accessToken = UUID.randomUUID().toString();
//        SessionInfo sessionInfo = new SessionInfo.Builder(accessToken, user.getId())
//                .userAgent(userAgent)
//                .expirationTime(LocalDateTime.now().plusDays(30))
//                .build();
//
//        // DB, 캐시에 저장
//        SessionInfo saved = cacheModule.writeThrough(CacheEnv.SESSION_INFO, accessToken, sessionInfo, sessionInfoRepository::save);
//
//
//        return saved;
//    }
//
//
//    // 세션 키 삭제
//    @Override
//    public void removeSession(String accessToken) {
//        if (accessToken != null)
//            cacheModule.deleteThrough(CacheEnv.SESSION_INFO, accessToken, this::deleteIfExists);
//    }
//
//    private void deleteIfExists(String accessToken){
//        if(sessionInfoRepository.existsById(accessToken))
//            sessionInfoRepository.deleteById(accessToken);
//    }
//
//}
