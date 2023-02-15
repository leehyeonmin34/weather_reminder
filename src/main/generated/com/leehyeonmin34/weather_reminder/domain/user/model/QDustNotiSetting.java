package com.leehyeonmin34.weather_reminder.domain.user.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDustNotiSetting is a Querydsl query type for DustNotiSetting
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QDustNotiSetting extends BeanPath<DustNotiSetting> {

    private static final long serialVersionUID = 1628256744L;

    public static final QDustNotiSetting dustNotiSetting = new QDustNotiSetting("dustNotiSetting");

    public QDustNotiSetting(String variable) {
        super(DustNotiSetting.class, forVariable(variable));
    }

    public QDustNotiSetting(Path<? extends DustNotiSetting> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDustNotiSetting(PathMetadata metadata) {
        super(DustNotiSetting.class, metadata);
    }

}

