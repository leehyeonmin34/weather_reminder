package com.leehyeonmin34.weather_reminder.domain.user.repository;

import com.leehyeonmin34.weather_reminder.WeatherReminderApplication;
import com.leehyeonmin34.weather_reminder.domain.user.domain.QUser;
import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.domain.user.domain.UserBuilder;
import com.leehyeonmin34.weather_reminder.domain.weather_info.domain.QWeatherInfo;
import com.leehyeonmin34.weather_reminder.global.config.TestProfile;
import com.leehyeonmin34.weather_reminder.global.test_config.IntegrationTest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTypeExcludeFilter;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@ActiveProfiles(TestProfile.TEST)
@DataJpaTest
@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest{

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager em;

    private JPAQueryFactory queryFactory;

    private User user;

    @BeforeEach
    public void init(){
        user = userRepository.save(UserBuilder.buildByOneRegion());
        queryFactory = new JPAQueryFactory(em);
    }

    @AfterEach
    public void teardown(){
        userRepository.delete(user);
    }

    @Test
    public void findByIdTest(){

        // WHEN
        Long id = user.getId();
        User result = userRepository.findById(id).get();

        // THEN
        System.out.println(result);
        then(result).isNotNull();

    }

    @Test
    public void findByIdQueryDslTest(){
        // WHEN
        long result = queryFactory.selectFrom(QUser.user)
                .where(QUser.user.id.eq(user.getId()))
                .fetch().stream().count();

        // THEN
        System.out.println(result);
        then(result).isNotNull();
    }

}
