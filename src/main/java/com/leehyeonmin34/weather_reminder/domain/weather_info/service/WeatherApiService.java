package com.leehyeonmin34.weather_reminder.domain.weather_info.service;

import com.leehyeonmin34.weather_reminder.domain.user.model.Region;
import com.leehyeonmin34.weather_reminder.domain.weather_info.domain.WeatherInfo;
import com.leehyeonmin34.weather_reminder.domain.weather_info.dto.WeatherApiResponseDto;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion;
import com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType;
import com.leehyeonmin34.weather_reminder.domain.weather_info.repository.WeatherInfoRepository;
import com.leehyeonmin34.weather_reminder.global.api.exception.OpenApiException;
import com.leehyeonmin34.weather_reminder.global.api.exception.OpenApiResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WeatherApiService {

    public static String HOST = "http://apis.data.go.kr";

    public static String RESOURCE_PATH = "/1360000/NwpModelInfoService/getLdapsUnisArea";

    public static String URL = HOST + RESOURCE_PATH;

    public static String SERVICE_KEY = "pe8XHm8%2FGHK4tZYj1JjMgKqNLAq%2FvEGui%2Feavz5uDz%2Bmm%2BfBk0aurh6fawL0PNqexuMpmlAGv3qcW8wRMGoKGQ%3D%3D";

    public static String DECODED_SERVICE_KEY = URLDecoder.decode(SERVICE_KEY, Charset.forName("UTF-8"));

    private final RestTemplate restTemplate;

    public WeatherApiService(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<WeatherInfo> getWeatherInfo(final Region region, final WeatherDataType weatherDataType){
        final WeatherApiResponseDto dto = getApi(region, weatherDataType);

        validateResponse(dto);

        // 조회 당일의 날씨만 저장하기 위한 기준 지정
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime dayAfter = now.plusDays(1);
        final String dayStart = WeatherApiTimeConverter.serialize(LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 00, 00));
        final String dayEnd = WeatherApiTimeConverter.serialize(LocalDateTime.of(dayAfter.getYear(), dayAfter.getMonthValue(), dayAfter.getDayOfMonth(), 00, 00));

        return dto.getResponse().getBody().getItems().getItem().stream()
                .filter(item -> item.getFcstTime().compareTo(dayStart) >= 0 && item.getFcstTime().compareTo(dayEnd) < 0)
                .map(item -> new WeatherInfo(item.getBaseTime(), item.getFcstTime(), region.getWeatherRegion(), weatherDataType, item.getValue()))
                .collect(Collectors.toList());
    }

    private void validateResponse(final WeatherApiResponseDto dto){
        try {
            final String resultCode = dto.getResponse().getHeader().getResultCode();
            if (!resultCode.equals(OpenApiResponseStatus.NORMAL_SERVICE.getCode()))
                throw new OpenApiException(resultCode);
        } catch(NullPointerException e){
            throw new OpenApiException();
        }
    }

    public WeatherApiResponseDto getApi(final Region region, final WeatherDataType weatherDataType) {
        return getApi(URL, region, weatherDataType);
    }

    public WeatherApiResponseDto getApi(final String url, final Region region, final WeatherDataType weatherDataType){
        final URI uri = getUri(url, region, weatherDataType);

        log.info(uri.toString());

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        final HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, WeatherApiResponseDto.class).getBody();
    }

    public URI getUri(final String url, final Region region, final WeatherDataType weatherDataType){

        return UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("serviceKey", SERVICE_KEY)
                .queryParam("numOfRows", "49")
                .queryParam("dataType", "json")
                .queryParam("baseTime", parseIntoBaseTime(LocalDateTime.now()))
                .queryParam("dongCode", region.getWeatherRegion().getDongCode())
                .queryParam("dataTypeCd", weatherDataType.getCode())
                .queryParam("pageNo", String.valueOf(1))
                .build(true).toUri();
    }

    public String getUriString(final String url, final Region region, final WeatherDataType weatherDataType){
        return getUri(url, region, weatherDataType).toString();
    }



    private String parseIntoBaseTime(final LocalDateTime lookupTime){
        // 새벽 5시에 배치로 API를 요청한다. 최신 데이터인 전날 21시 데이터를 조회
        final LocalDateTime baseTime = extractBaseDateTime(lookupTime);
        return WeatherApiTimeConverter.serialize(LocalDateTime.of(baseTime.getYear(), baseTime.getMonthValue(), baseTime.getDayOfMonth(), 21, 00));
    }

    private LocalDateTime extractBaseDateTime(final LocalDateTime lookupTime){
        // 조회 시간이 오전 3시 이전이라면 전전날 21시 기준으로 조회해야한다
        if (lookupTime.getHour() < 3)
            return lookupTime.minusDays(2);
        else // 전날 21시 기준 으로 요청해야하기 때문에 -1일
            return lookupTime.minusDays(1);
    }



}

