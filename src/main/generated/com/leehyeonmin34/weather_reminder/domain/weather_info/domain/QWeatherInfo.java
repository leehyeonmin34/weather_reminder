package com.leehyeonmin34.weather_reminder.domain.weather_info.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWeatherInfo is a Querydsl query type for WeatherInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWeatherInfo extends EntityPathBase<WeatherInfo> {

    private static final long serialVersionUID = -1143885275L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWeatherInfo weatherInfo = new QWeatherInfo("weatherInfo");

    public final DateTimePath<java.time.LocalDateTime> baseTime = createDateTime("baseTime", java.time.LocalDateTime.class);

    public final QWeatherInfoPk id;

    public final NumberPath<Float> value = createNumber("value", Float.class);

    public QWeatherInfo(String variable) {
        this(WeatherInfo.class, forVariable(variable), INITS);
    }

    public QWeatherInfo(Path<? extends WeatherInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWeatherInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWeatherInfo(PathMetadata metadata, PathInits inits) {
        this(WeatherInfo.class, metadata, inits);
    }

    public QWeatherInfo(Class<? extends WeatherInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QWeatherInfoPk(forProperty("id")) : null;
    }

}

