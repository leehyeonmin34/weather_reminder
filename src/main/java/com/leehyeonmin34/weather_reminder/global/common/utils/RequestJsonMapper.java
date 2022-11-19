package com.leehyeonmin34.auth_practice.global.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestJsonMapper {
    final private ObjectMapper objectMapper;

    public <T> T mapToJson(ServletRequest request, Class<T> clazz){
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader br = null;
        String line;
        try {
            InputStream is = request.getInputStream();
            if (is != null){
                br = new BufferedReader(new InputStreamReader(is));
                while((line = br.readLine()) != null){
                    stringBuilder.append(line);
                }
            } else {
                log.info("Data 없음");
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        T object = null;
        try {
            object = objectMapper.readValue(stringBuilder.toString(), clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return object;

    }

}
