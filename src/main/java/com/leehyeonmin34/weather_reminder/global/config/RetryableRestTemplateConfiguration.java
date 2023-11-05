package com.leehyeonmin34.weather_reminder.global.config;

import com.leehyeonmin34.weather_reminder.global.api.exception.OpenApiException;
import com.leehyeonmin34.weather_reminder.global.api.exception.OpenApiResponseStatus;
import com.leehyeonmin34.weather_reminder.global.common.StandardResponse;
import com.leehyeonmin34.weather_reminder.global.error.ErrorResponse;
import com.leehyeonmin34.weather_reminder.global.error.exception.ErrorCode;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@EnableRetry
@Configuration
public class RetryableRestTemplateConfiguration {

    @Bean(name = "restTemplate")
    public RestTemplate retryableRestTemplate() {
        SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setReadTimeout(20000);
        clientHttpRequestFactory.setConnectTimeout(5000);

        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory) {
            @Override
            @Retryable(value = RestClientException.class, maxAttempts = 3, backoff = @Backoff(delay = 60000))
            public <T> ResponseEntity<T> exchange(URI url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType)
                    throws RestClientException {
                return super.exchange(url, method, requestEntity, responseType);
            }

            @Recover
            public <T> ResponseEntity<T> exchangeRecover(RestClientException e) {
                throw new OpenApiException(OpenApiResponseStatus.UNKNOWN_ERROR.getCode());
            }
        };

        return restTemplate;
    }
}
