package com.leehyeonmin34.weather_reminder.domain.weather_info.service;

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

    private final WeatherInfoRepository weatherInfoRepository;

    public WeatherApiService(RestTemplateBuilder restTemplateBuilder, WeatherInfoRepository weatherInfoRepository) {
        this.restTemplate = restTemplateBuilder.build();
        this.weatherInfoRepository = weatherInfoRepository;
    }


    public void loadAndSaveTodayWeatherInfo(){
        Arrays.stream(WeatherRegion.values()).forEach(dong ->
                Arrays.stream(WeatherDataType.values()).forEach( weatherDataType ->
                        loadAndSaveWeatherInfo(dong, weatherDataType))
        );
    }

    private void loadAndSaveWeatherInfo(WeatherRegion weatherRegion, WeatherDataType weatherDataType){
        WeatherApiResponseDto dto = getApi(weatherRegion, weatherDataType);

        validateResponse(dto);

        List<WeatherInfo> weatherInfoList = dto.getResponse().getBody().getItems().getItem().stream()
                .map(item -> new WeatherInfo(item.getBaseTime(), item.getFcstTime(), weatherRegion, weatherDataType, item.getValue()))
                .collect(Collectors.toList());

        weatherInfoRepository.saveAll(weatherInfoList);
    }

    private void validateResponse(WeatherApiResponseDto dto){
        try {
            String resultCode = dto.getResponse().getHeader().getResultCode();
            if (!resultCode.equals(OpenApiResponseStatus.NORMAL_SERVICE.getCode()))
                throw new OpenApiException(resultCode);
        } catch(NullPointerException e){
            throw new OpenApiException();
        }
    }

    public WeatherApiResponseDto getApi(WeatherRegion weatherRegion, WeatherDataType weatherDataType) {
        return getApi(URL, weatherRegion, weatherDataType);
    }

    public WeatherApiResponseDto getApi(String url, WeatherRegion weatherRegion, WeatherDataType weatherDataType){
        URI uri = getUri(url, weatherRegion, weatherDataType);

        log.info(uri.toString());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, WeatherApiResponseDto.class).getBody();
    }

    public URI getUri(String url, WeatherRegion weatherRegion, WeatherDataType weatherDataType){

        return UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("serviceKey", SERVICE_KEY)
                .queryParam("numOfRows", "24")
                .queryParam("dataType", "json")
                .queryParam("baseTime", parseIntoBaseTime(LocalDateTime.now()))
                .queryParam("dongCode", weatherRegion.getDongCode())
                .queryParam("dataTypeCd", weatherDataType.getCode())
                .queryParam("pageNo", String.valueOf(1))
                .build(true).toUri();
    }

    private String parseIntoBaseTime(LocalDateTime localDateTime){
        // 새벽 5시에 배치로 API를 요청한다. 최신 데이터인 전날 21시 데이터를 조회
        final LocalDateTime baseTime = extractBaseDateTime(localDateTime);

        final String yearString = String.valueOf(baseTime.getYear());
        final String monthString = String.valueOf(baseTime.getMonthValue());
        final String dayString = String.valueOf(baseTime.getDayOfMonth());

        final String month = monthString.length() == 1 ? "0" + monthString : monthString; // 2글자
        final String day = dayString.length() == 1 ? "0" + dayString : dayString; // 2글자
        final String time = "2100"; // HHMM, 조회 기준 시간은 오후 9시로 고정 (새벽 5시에 배치로 조회한다고 했을때 가장 최신 데이터임)

        return yearString + month + day + time; // 조회 기준 시간은 오후 9시로 고정 (새벽 5시에 배치로 조회한다고 했을때 가장 최신 데이터임)
    }

    private LocalDateTime extractBaseDateTime(final LocalDateTime localDateTime){
        // 조회 시간이 오전 3시 이전이라면 전전날 21시 기준으로 조회해야한다
        if (localDateTime.getHour() < 3)
            return localDateTime.minusDays(2);
        else // 전날 21시 기준 으로 요청해야하기 때문에 -1일
            return localDateTime.minusDays(1);
    }

    public String getUriString(String url, WeatherRegion weatherRegion, WeatherDataType weatherDataType){
        return getUri(url, weatherRegion, weatherDataType).toString();
    }

}

