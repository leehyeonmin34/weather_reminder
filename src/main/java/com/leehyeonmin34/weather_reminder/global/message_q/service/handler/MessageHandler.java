package com.leehyeonmin34.weather_reminder.global.message_q.service.handler;

import com.leehyeonmin34.weather_reminder.global.message_q.model.Message;

public interface MessageHandler {

    void handle(final Message message);
}
