package com.leehyeonmin34.weather_reminder.domain.user.repository;

import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
