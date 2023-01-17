package com.leehyeonmin34.weather_reminder.global;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTestDomain is a Querydsl query type for TestDomain
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTestDomain extends EntityPathBase<TestDomain> {

    private static final long serialVersionUID = 1062231327L;

    public static final QTestDomain testDomain = new QTestDomain("testDomain");

    public final NumberPath<Integer> column2 = createNumber("column2", Integer.class);

    public final NumberPath<Integer> column3 = createNumber("column3", Integer.class);

    public final NumberPath<Integer> column4 = createNumber("column4", Integer.class);

    public final NumberPath<Integer> column5 = createNumber("column5", Integer.class);

    public final NumberPath<Integer> column6 = createNumber("column6", Integer.class);

    public final NumberPath<Integer> column7 = createNumber("column7", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final DateTimePath<java.time.LocalDateTime> time = createDateTime("time", java.time.LocalDateTime.class);

    public QTestDomain(String variable) {
        super(TestDomain.class, forVariable(variable));
    }

    public QTestDomain(Path<? extends TestDomain> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTestDomain(PathMetadata metadata) {
        super(TestDomain.class, metadata);
    }

}

