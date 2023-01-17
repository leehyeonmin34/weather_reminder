package com.leehyeonmin34.weather_reminder.global.message_q.service;

import com.leehyeonmin34.weather_reminder.global.message_q.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class MessageFailHandler {

    private final MessageQ messageQ;

    public void handleFail(Message message, Exception e){
        message.addException(e);
        message.increaseFailCount();
        if(message.getFailCount() >= message.getTolerance())
            handleTooManyFails(message);
        else
            messageQ.add(message);
    }

    private void handleTooManyFails(Message message){
        log.info("최대 실패 횟수로 지정한 " + message.getTolerance() + "회를 초과했습니다.");
        log.info("예외 리스트---------------------------------------");
        List<Exception> exceptionList = message.getExceptionList();
        log.info("실패한 메시지 : " + message);
        for(int i = 0; i < message.getExceptionList().size(); i++){
            Exception e = exceptionList.get(i);
            e.printStackTrace();
            log.info("----------------------------------");
        }
        log.info("예외 리스트 끝 -----------------------------------");
    }


}
