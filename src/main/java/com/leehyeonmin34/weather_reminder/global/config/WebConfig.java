package com.leehyeonmin34.weather_reminder.global.config;

import com.leehyeonmin34.weather_reminder.domain.session_info.service.SessionService;
import com.leehyeonmin34.weather_reminder.global.filter.ExceptionHandlerFilter;
import com.leehyeonmin34.weather_reminder.global.filter.LoginCheckFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    
}