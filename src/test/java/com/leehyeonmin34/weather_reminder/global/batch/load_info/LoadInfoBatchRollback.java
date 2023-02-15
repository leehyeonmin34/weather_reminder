package com.leehyeonmin34.weather_reminder.global.batch.load_info;

import com.leehyeonmin34.weather_reminder.domain.weather_info.domain.QWeatherInfo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@RequiredArgsConstructor
@Component
public class LoadInfoBatchRollback {

    private final EntityManagerFactory entityManagerFactory;

    private final JPAQueryFactory jpaQueryFactory;

    @Transactional(readOnly = false)
    public void rollback(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        jpaQueryFactory
                .delete(QWeatherInfo.weatherInfo)
                .where(QWeatherInfo.weatherInfo.value.gt(99900))
                .execute();
        transaction.commit();
    }
}
