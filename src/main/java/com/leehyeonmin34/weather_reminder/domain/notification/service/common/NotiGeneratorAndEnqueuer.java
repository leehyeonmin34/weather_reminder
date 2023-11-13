package com.leehyeonmin34.weather_reminder.domain.notification.service.common;

import com.leehyeonmin34.weather_reminder.domain.notification.domain.Notification;
import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.global.message_q.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class NotiGeneratorAndEnqueuer {

    final private NotiGenerator notiGenerator;
    final private NotiEnqueuer notiEnqueuer;

    public Optional<Message<Notification>> generateNotiAndEnqueue(final User user){
        final Notification noti = notiGenerator.generate(user).get();
        return notiEnqueuer.enqueue(noti);
    };





}
