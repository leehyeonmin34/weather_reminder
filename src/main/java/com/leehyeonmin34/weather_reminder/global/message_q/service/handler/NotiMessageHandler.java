package com.leehyeonmin34.weather_reminder.global.message_q.service.handler;

import com.leehyeonmin34.weather_reminder.domain.notification.domain.Notification;
import com.leehyeonmin34.weather_reminder.domain.notification.service.common.NotiSender;
import com.leehyeonmin34.weather_reminder.global.message_q.model.Message;
import com.leehyeonmin34.weather_reminder.global.message_q.model.MessageType;
import com.leehyeonmin34.weather_reminder.global.message_q.service.MessageFailHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotiMessageHandler implements MessageHandler {

    private final NotiSender notiSender;

    private final MessageFailHandler failHandler;

    @Override
    public void handle(final Message message) {
        if (message.getMessageType() != MessageType.NOTI)
            return;

        notiSender.send((Notification) message.getContent());

        log.info("메시지가 NotiMessageHandler에 의해 처리되었습니다.");
    }
}
