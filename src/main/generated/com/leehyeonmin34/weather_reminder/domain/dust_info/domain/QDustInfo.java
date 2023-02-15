package com.leehyeonmin34.weather_reminder.domain.dust_info.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDustInfo is a Querydsl query type for DustInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDustInfo extends EntityPathBase<DustInfo> {

    private static final long serialVersionUID = -980957073L;

    public static final QDustInfo dustInfo = new QDustInfo("dustInfo");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QDustInfo(String variable) {
        super(DustInfo.class, forVariable(variable));
    }

    public QDustInfo(Path<? extends DustInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDustInfo(PathMetadata metadata) {
        super(DustInfo.class, metadata);
    }

}

