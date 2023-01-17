package com.leehyeonmin34.weather_reminder.global.batch.send_noti;

import com.leehyeonmin34.weather_reminder.domain.user.domain.QUser;
import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

@RequiredArgsConstructor
@Component
public class SendNotiRollback {

    private final EntityManagerFactory entityManagerFactory;

    private final JPAQueryFactory query;

    @Transactional(readOnly = false)
    public void rollback(List<User> users){
        // ROLLBACK
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        query.delete(QUser.user)
                .where(QUser.user.in(users))
                .execute();
        transaction.commit();
    }
}
