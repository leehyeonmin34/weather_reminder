package com.leehyeonmin34.weather_reminder.domain.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1133175930L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final com.leehyeonmin34.weather_reminder.domain.user.model.QColdNotiSetting coldNotiSetting;

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final com.leehyeonmin34.weather_reminder.domain.user.model.QHotNotiSetting hotNotiSetting;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final SimplePath<com.leehyeonmin34.weather_reminder.global.common.model.DayTime> notiTime = createSimple("notiTime", com.leehyeonmin34.weather_reminder.global.common.model.DayTime.class);

    public final DateTimePath<java.time.LocalDateTime> pauseUntil = createDateTime("pauseUntil", java.time.LocalDateTime.class);

    public final com.leehyeonmin34.weather_reminder.domain.user.model.QRainNotiSetting rainNotiSetting;

    public final ListPath<com.leehyeonmin34.weather_reminder.domain.user.model.Region, EnumPath<com.leehyeonmin34.weather_reminder.domain.user.model.Region>> regionList = this.<com.leehyeonmin34.weather_reminder.domain.user.model.Region, EnumPath<com.leehyeonmin34.weather_reminder.domain.user.model.Region>>createList("regionList", com.leehyeonmin34.weather_reminder.domain.user.model.Region.class, EnumPath.class, PathInits.DIRECT2);

    public final com.leehyeonmin34.weather_reminder.domain.user.model.QSnowNotiSetting snowNotiSetting;

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.coldNotiSetting = inits.isInitialized("coldNotiSetting") ? new com.leehyeonmin34.weather_reminder.domain.user.model.QColdNotiSetting(forProperty("coldNotiSetting")) : null;
        this.hotNotiSetting = inits.isInitialized("hotNotiSetting") ? new com.leehyeonmin34.weather_reminder.domain.user.model.QHotNotiSetting(forProperty("hotNotiSetting")) : null;
        this.rainNotiSetting = inits.isInitialized("rainNotiSetting") ? new com.leehyeonmin34.weather_reminder.domain.user.model.QRainNotiSetting(forProperty("rainNotiSetting")) : null;
        this.snowNotiSetting = inits.isInitialized("snowNotiSetting") ? new com.leehyeonmin34.weather_reminder.domain.user.model.QSnowNotiSetting(forProperty("snowNotiSetting")) : null;
    }

}

