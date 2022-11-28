package com.leehyeonmin34.weather_reminder.domain.notification.repository;

import com.leehyeonmin34.weather_reminder.domain.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
