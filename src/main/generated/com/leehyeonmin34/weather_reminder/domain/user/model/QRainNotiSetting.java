package com.leehyeonmin34.weather_reminder.domain.user.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRainNotiSetting is a Querydsl query type for RainNotiSetting
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QRainNotiSetting extends BeanPath<RainNotiSetting> {

    private static final long serialVersionUID = 644949958L;

    public static final QRainNotiSetting rainNotiSetting = new QRainNotiSetting("rainNotiSetting");

    public final NumberPath<Byte> conditionTime = createNumber("conditionTime", Byte.class);

    public final BooleanPath on = createBoolean("on");

    public QRainNotiSetting(String variable) {
        super(RainNotiSetting.class, forVariable(variable));
    }

    public QRainNotiSetting(Path<? extends RainNotiSetting> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRainNotiSetting(PathMetadata metadata) {
        super(RainNotiSetting.class, metadata);
    }

}

