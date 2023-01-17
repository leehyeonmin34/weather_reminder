package com.leehyeonmin34.weather_reminder.global.service;

import com.leehyeonmin34.weather_reminder.global.common.domain.CacheModuleTestEntity;
import com.leehyeonmin34.weather_reminder.global.common.service.CacheModule;
import com.leehyeonmin34.weather_reminder.global.cache.CacheEnv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@WebAppConfiguration
//@Transactional
//@ExtendWith(MockitoExtension.class)
public class CacheModuleTest {

    @Autowired
    private CacheModule cacheModule;

//    @Spy
//    @Qualifier("cacheManager")
    @Autowired
    private CacheManager cacheManager;

    private static final CacheModuleTestEntity cached = new CacheModuleTestEntity("cached_key", "cached_value");
    private static final String cachedKey = cached.getKey();
    private static final CacheModuleTestEntity notCached = new CacheModuleTestEntity("not_cached_key", "not_cached_value");
    private static final String notCachedKey = notCached.getKey();
    private static final String cacheName = CacheEnv.TEST;

    @BeforeEach
    public void init(){
//      cached만 캐시에 저장
        cacheManager.getCache(cacheName).put(cachedKey, cached);
    }

    @AfterEach
    public void after(){
        cacheManager.getCache(cacheName).evictIfPresent(cachedKey);
    }


    @ParameterizedTest(name = "{index} : {0}")
    @MethodSource("getCacheOrLoadTestCondition")
    public void getCacheOrLoadTest(String caseName, boolean cacheExists, String cacheNameToGet){

        // GIVEN
        final String key = cacheExists ? cachedKey : notCachedKey;
        final CacheModuleTestEntity expectedFoundEntity = cacheExists ? cached : notCached;
        final Cache cache = cacheManager.getCache(cacheNameToGet);

        // WHEN
        try {
            CacheModuleTestEntity result = cacheModule.getCacheOrLoad(cacheNameToGet, key
                    , k -> {/* DB 조회 로직 */ return expectedFoundEntity;});

            // THEN
            CacheModuleTestEntity onCache = (CacheModuleTestEntity) cache.get(key).get();
            then(result).isNotNull();
            then(onCache).isNotNull();
            then(result).isEqualTo(onCache);


        } catch(IllegalArgumentException e){
            then(cacheNameToGet).isEqualTo("Undeclared CacheName");
        } finally {
            // ROLLBACK
            if (cache != null)
                cache.evictIfPresent(key);
        }
    }


    private static Stream getCacheOrLoadTestCondition(){

        return Stream.of(
                Arguments.arguments("성공 - cache hit", true, cacheName),
                Arguments.arguments("성공 - cache miss, db hit", false, cacheName),
                Arguments.arguments("실패 - 선언되지 않은 캐시 이름", false, "Undeclared CacheName")
                );
    }

    @ParameterizedTest(name = "{index} : {0}")
    @MethodSource("writeThroughTestConditions")
    @DisplayName("writeThrough 테스트")
    public void writeThroughTest(String caseName, boolean cacheExists, CacheModuleTestEntity value){
        // GIVEN
        final CacheModuleTestEntity valueToWrite = cacheExists ? modifiedValue(value) : value;
        final String key = valueToWrite.getKey();
        Cache cache = cacheManager.getCache(cacheName);

        // WHEN
        CacheModuleTestEntity result = cacheModule.writeThrough(cacheName, key, valueToWrite, k -> { /* DB 저장 로직 */ return valueToWrite;});

        // THEN
        CacheModuleTestEntity cached = cache.get(key, CacheModuleTestEntity.class);
        then(cached).isEqualTo(valueToWrite); // DB에 저장한 내용 == 캐시에 저장된 내용
        then(cached).isEqualTo(result); // DB에 저장된 내용 == 캐시에 저장된 내용

        // ROLLBACK
        cache.evictIfPresent(key);
    }

    private CacheModuleTestEntity modifiedValue(CacheModuleTestEntity original){
        return new CacheModuleTestEntity(original.getKey(), "modified_value");
    }

    public static Stream writeThroughTestConditions(){
        return Stream.of(
                Arguments.arguments("성공 - 캐시에 없던 데이터 추가", false, notCached),
                Arguments.arguments("성공 - 캐시에 있던 데이터 갱신", true, cached)
        );
    }


    @ParameterizedTest(name="{index} : {0}")
    @MethodSource("deleteThroughTestConditions")
    @DisplayName("deleteThrough 테스트")
    public void deleteThroughTest(String caseName, CacheModuleTestEntity value){
        // GIVEN
        final String key = value.getKey();
        Cache cache = cacheManager.getCache(cacheName);

        // WHEN
        cacheModule.deleteThrough(cacheName, key, k -> { /* DB 삭제 로직 */ });

        // THEN
        CacheModuleTestEntity newCreatedCache = cache.get(key, CacheModuleTestEntity.class);
        then(newCreatedCache).isNull();
    }

    public static Stream deleteThroughTestConditions(){
        return Stream.of(
                Arguments.arguments("성공 - 캐시에 존재했던 데이터 삭제", cached),
                Arguments.arguments("성공 - 캐시에 존재하지 않은 데이터 삭제", notCached)
                );
    }

    @ParameterizedTest(name="{index} : {0}")
    @MethodSource("getTestConditions")
    @DisplayName("get 테스트")
    public void getTest(String caseName, boolean cacheExists, CacheModuleTestEntity value){
        // GIVEN
        String key = value.getKey();

        // WHEN
        cacheModule.get(cacheName, key);

        // THEN
        Cache cache = cacheManager.getCache(cacheName);
        CacheModuleTestEntity cached = cache.get(key, CacheModuleTestEntity.class);
        if (cacheExists)
            then(cached).isEqualTo(value);
        else then(cached).isNull();
    }

    public static Stream getTestConditions(){
        return Stream.of(
                Arguments.arguments("성공 - 캐시에 존재했던 데이터 조회", true, cached),
                Arguments.arguments("성공 - 캐시에 존재하지 않은 데이터 조회", false, notCached)
        );
    }

    @ParameterizedTest(name="{index} : {0}")
    @MethodSource("putTestConditions")
    @DisplayName("put 테스트")
    public void putTest(String caseName, boolean cacheExists, CacheModuleTestEntity value){
        // GIVEN
        final CacheModuleTestEntity valueToWrite = cacheExists ? modifiedValue(value) : value;
        final String key = valueToWrite.getKey();
        Cache cache = cacheManager.getCache(cacheName);


        // WHEN
        cacheModule.put(cacheName, key, valueToWrite);

        // THEN
        CacheModuleTestEntity cached = cache.get(key, CacheModuleTestEntity.class);
        then(cached).isEqualTo(valueToWrite); // DB에 저장한 내용 == 캐시에 저장된 내용

        // ROLLBACK
        cache.evictIfPresent(key);
    }

    public static Stream putTestConditions(){
        return Stream.of(
                Arguments.arguments("성공 - 캐시에 존재했던 데이터 수정", true, cached),
                Arguments.arguments("성공 - 캐시에 존재하지 않은 데이터 추가", false, notCached)
        );
    }

    @ParameterizedTest(name="{index} : {0}")
    @MethodSource("evictTestConditions")
    @DisplayName("evict 테스트")
    public void evictTest(String caseName, CacheModuleTestEntity value){
        // GIVEN
        Cache cache = cacheManager.getCache(cacheName);
        String key = value.getKey();

        // WHEN
        cacheModule.evict(cacheName, key);

        // THEN
        CacheModuleTestEntity cached = cache.get(key, CacheModuleTestEntity.class);
        then(cached).isNull();

        // ROLLBACK
        cache.evictIfPresent(key);
    }

    public static Stream evictTestConditions(){
        return Stream.of(
                Arguments.arguments("성공 - 캐시에 존재했던 데이터 삭제", cached),
                Arguments.arguments("성공 - 캐시에 존재하지 않은 데이터 삭제 (예외 발생 X)", notCached)
        );
    }




}
