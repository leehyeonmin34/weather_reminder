package com.leehyeonmin34.weather_reminder.global.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leehyeonmin34.weather_reminder.domain.session_info.service.SessionService;
import com.leehyeonmin34.weather_reminder.global.common.AccessTokenDto;
import com.leehyeonmin34.weather_reminder.global.error.exception.ErrorCode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class LoginCheckFilterTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    SessionService sessionService;

    @Autowired
    ObjectMapper objectMapper;

    @ParameterizedTest(name = "{index} : {0}")
    @MethodSource("loginCheckFilterTestConditions")
    public void loginCheckFilterTest(String caseName, String accessToken, boolean existsByToken, boolean expectedTestResult) throws JsonProcessingException {
        // GIVEN
        doReturn(existsByToken).when(sessionService).existsByToken(any(String.class));
        ResultActions resultActions;
        AccessTokenDto accessTokenDto = new AccessTokenDto(accessToken);
        String requestJSON = objectMapper.writeValueAsString(accessTokenDto);

        // WHEN
        try {
            resultActions = mvc.perform(MockMvcRequestBuilders.get("/api/am_i_logged_in")
                    .contentType("application/json")
                    .content(requestJSON)
            );
            // THEN - SUCCESS
            if(expectedTestResult){
                then(existsByToken).isTrue();
                then(accessToken).isNotNull();
                resultActions
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("status").value(HttpStatus.OK.value()))
                        .andExpect(jsonPath("data").value("로그인된 사용자가 맞습니다."));
            } else{
            // THEN - FAIL
                then(expectedTestResult).isEqualTo(false);
                resultActions
                    .andExpect(status().is(401))
                    .andExpect(jsonPath("status").value(HttpStatus.UNAUTHORIZED.value()))
                    .andExpect(jsonPath("data").doesNotExist())
                    .andExpect(jsonPath("errorResponse.errorCode").value(ErrorCode.NOT_LOGGED_IN.getCode()));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private static Stream loginCheckFilterTestConditions(){
        return Stream.of(
                Arguments.arguments("성공","access_token", true, true),
                Arguments.arguments("실패(미존재 토큰)","access_token", false,false),
                Arguments.arguments("실패(토큰 미포함 요청)", null, true, false),
                Arguments.arguments("실패(토큰 미포함 요청2)", null, false, false)
        );
    }






}
