package com.leehyeonmin34.weather_reminder.global.config;

import com.leehyeonmin34.auth_practice.domain.user.service.SessionService;
import com.leehyeonmin34.auth_practice.global.common.utils.RequestJsonMapper;
import com.leehyeonmin34.auth_practice.global.filter.ExceptionHandlerFilter;
import com.leehyeonmin34.auth_practice.global.filter.LoginCheckFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    final private SessionService sessionService;
    final private RequestJsonMapper requestJsonMapper;

    @Bean
    public FilterRegistrationBean filterExceptionHandlerFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new ExceptionHandlerFilter()); //내가 구현한 필터 넣기
        filterRegistrationBean.setOrder(1); //필터 체인할 때 가장 먼저 실행
        filterRegistrationBean.addUrlPatterns("/*"); //모든 요청 url에 대해 실행
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean loginFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter(sessionService, requestJsonMapper)); //내가 구현한 필터 넣기
        filterRegistrationBean.setOrder(2); //필터 체인할 때 두번째 먼저 실행
        filterRegistrationBean.addUrlPatterns("/*"); //모든 요청 url에 대해 실행
        return filterRegistrationBean;
    }




}