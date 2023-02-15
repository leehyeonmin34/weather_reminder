package com.leehyeonmin34.weather_reminder.domain.user.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QColdNotiSetting is a Querydsl query type for ColdNotiSetting
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QColdNotiSetting extends BeanPath<ColdNotiSetting> {

    private static final long serialVersionUID = -1152413066L;

    public static final QColdNotiSetting coldNotiSetting = new QColdNotiSetting("coldNotiSetting");

    public final NumberPath<Byte> conditionCelcius = createNumber("conditionCelcius", Byte.class);

    public final BooleanPath on = createBoolean("on");

    public QColdNotiSetting(String variable) {
        super(ColdNotiSetting.class, forVariable(variable));
    }

    public QColdNotiSetting(Path<? extends ColdNotiSetting> path) {
        super(path.getType(), path.getMetadata());
    }

    public QColdNotiSetting(PathMetadata metadata) {
        super(ColdNotiSetting.class, metadata);
    }

}

