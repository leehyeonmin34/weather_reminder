package com.leehyeonmin34.weather_reminder.domain.user.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QHotNotiSetting is a Querydsl query type for HotNotiSetting
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QHotNotiSetting extends BeanPath<HotNotiSetting> {

    private static final long serialVersionUID = 798710893L;

    public static final QHotNotiSetting hotNotiSetting = new QHotNotiSetting("hotNotiSetting");

    public final NumberPath<Byte> conditionCelcius = createNumber("conditionCelcius", Byte.class);

    public final BooleanPath on = createBoolean("on");

    public QHotNotiSetting(String variable) {
        super(HotNotiSetting.class, forVariable(variable));
    }

    public QHotNotiSetting(Path<? extends HotNotiSetting> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHotNotiSetting(PathMetadata metadata) {
        super(HotNotiSetting.class, metadata);
    }

}

