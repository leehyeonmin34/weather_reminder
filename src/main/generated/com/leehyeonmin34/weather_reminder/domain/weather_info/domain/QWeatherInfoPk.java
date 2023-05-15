package com.leehyeonmin34.weather_reminder.domain.weather_info.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWeatherInfoPk is a Querydsl query type for WeatherInfoPk
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QWeatherInfoPk extends BeanPath<WeatherInfoPk> {

    private static final long serialVersionUID = 237881088L;

    public static final QWeatherInfoPk weatherInfoPk = new QWeatherInfoPk("weatherInfoPk");

    public final DateTimePath<java.time.LocalDateTime> fcstTime = createDateTime("fcstTime", java.time.LocalDateTime.class);

    public final EnumPath<com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType> weatherDataType = createEnum("weatherDataType", com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherDataType.class);

    public final EnumPath<com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion> weatherRegion = createEnum("weatherRegion", com.leehyeonmin34.weather_reminder.domain.weather_info.model.WeatherRegion.class);

    public QWeatherInfoPk(String variable) {
        super(WeatherInfoPk.class, forVariable(variable));
    }

    public QWeatherInfoPk(Path<? extends WeatherInfoPk> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWeatherInfoPk(PathMetadata metadata) {
        super(WeatherInfoPk.class, metadata);
    }

}

