package com.leehyeonmin34.weather_reminder.domain.session_info.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSessionInfo is a Querydsl query type for SessionInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSessionInfo extends EntityPathBase<SessionInfo> {

    private static final long serialVersionUID = -1859985303L;

    public static final QSessionInfo sessionInfo = new QSessionInfo("sessionInfo");

    public final StringPath accessToken = createString("accessToken");

    public final DateTimePath<java.time.LocalDateTime> expirationTime = createDateTime("expirationTime", java.time.LocalDateTime.class);

    public final StringPath userAgent = createString("userAgent");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QSessionInfo(String variable) {
        super(SessionInfo.class, forVariable(variable));
    }

    public QSessionInfo(Path<? extends SessionInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSessionInfo(PathMetadata metadata) {
        super(SessionInfo.class, metadata);
    }

}

