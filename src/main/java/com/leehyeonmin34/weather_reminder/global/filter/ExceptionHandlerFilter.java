package com.leehyeonmin34.weather_reminder.global.filter;

import com.google.gson.Gson;
import com.leehyeonmin34.weather_reminder.domain.user.exception.NotLoggedInException;
import com.leehyeonmin34.weather_reminder.global.common.StandardResponse;
import com.leehyeonmin34.weather_reminder.global.error.ErrorResponse;
import com.leehyeonmin34.weather_reminder.global.error.exception.BusinessException;
import com.leehyeonmin34.weather_reminder.global.error.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request,response);
        } catch (NotLoggedInException ex) {
            log.error("exception exception handler filter");
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, ex);
        }catch (RuntimeException ex){
            log.error("runtime exception exception handler filter");
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,response,ex);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, RuntimeException ex){
        // HTML이 UTF-8 형식이라는 것을 브라우저에게 전달
        response.setStatus(status.value());
        response.setContentType("application/json; charset=utf-8");
        ErrorCode errorCode = ex instanceof BusinessException ? ((BusinessException) ex).getErrorCode() : ErrorCode.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = ErrorResponse.of(errorCode, ex.getMessage());
        StandardResponse responseBody = StandardResponse.builder()
                .errorResponse(errorResponse)
                .status(errorCode.getStatus())
                .build();
        try{
            Gson gson = new Gson();
            String json = gson.toJson(responseBody);
            response.getWriter().write(json);
        }catch (IOException e){
            e.printStackTrace();
        }
    }



}
