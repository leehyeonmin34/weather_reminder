package com.leehyeonmin34.weather_reminder.domain.weather_info.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWeatherInfo is a Querydsl query type for WeatherInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWeatherInfo extends EntityPathBase<WeatherInfo> {

    private static final long serialVersionUID = -1143885275L;

    public static final QWeatherInfo weatherInfo = new QWeatherInfo("weatherInfo");

    public final DateTimePath<java.time.LocalDateTime> baseTime = createDateTime("baseTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> fcstTime = createDateTime("fcstTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Float> value = createNumber("value", Float.class);

    public final EnumPath<com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType> weatherDataType = createEnum("weatherDataType", com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType.class);

    public final EnumPath<com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion> weatherRegion = createEnum("weatherRegion", com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion.class);

    public QWeatherInfo(String variable) {
        super(WeatherInfo.class, forVariable(variable));
    }

    public QWeatherInfo(Path<? extends WeatherInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWeatherInfo(PathMetadata metadata) {
        super(WeatherInfo.class, metadata);
    }

}

