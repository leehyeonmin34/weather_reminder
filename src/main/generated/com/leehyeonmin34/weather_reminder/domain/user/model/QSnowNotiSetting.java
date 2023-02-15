package com.leehyeonmin34.weather_reminder.domain.user.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSnowNotiSetting is a Querydsl query type for SnowNotiSetting
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QSnowNotiSetting extends BeanPath<SnowNotiSetting> {

    private static final long serialVersionUID = -723466633L;

    public static final QSnowNotiSetting snowNotiSetting = new QSnowNotiSetting("snowNotiSetting");

    public final NumberPath<Byte> conditionTime = createNumber("conditionTime", Byte.class);

    public final BooleanPath on = createBoolean("on");

    public QSnowNotiSetting(String variable) {
        super(SnowNotiSetting.class, forVariable(variable));
    }

    public QSnowNotiSetting(Path<? extends SnowNotiSetting> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSnowNotiSetting(PathMetadata metadata) {
        super(SnowNotiSetting.class, metadata);
    }

}

