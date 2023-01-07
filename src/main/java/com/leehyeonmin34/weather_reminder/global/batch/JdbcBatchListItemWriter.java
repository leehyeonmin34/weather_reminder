package com.leehyeonmin34.weather_reminder.global.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

<<<<<<< HEAD

/*
    기존 ItemWriter는 Chunk단위의 List<T>를 받아 BatchInsert를 수행하는데,
    이 클래스는 List<? extends List<T>>를 인자로 받아, 각각의 T를 Bulk Insert를 수행하기 위한 클래스다.
    그러기 위해서, 이 클래스는 ItemWriter<T>가 아닌 ItemWriter<List<T>>를 구현하고, write 메서드를 오버라이드한다.
    write 메서드에서, 인자로 받은 List<? extends List<T>>를 flattening하고
    미리 주입된 ItemWriter<T>의 write 메서드를 호출해 BatchInsert를 수행한다.
*/
=======
>>>>>>> 589cb3d (날씨 정보 조회 배치 2)
@RequiredArgsConstructor
@Slf4j
public class JdbcBatchListItemWriter<T> implements ItemWriter<List<T>>, ItemStream, InitializingBean {

    private final JdbcBatchItemWriter<T> delegate;

    @Override
    public void write(List<? extends List<T>> items) throws Exception {
        final List<T> flattenedList = new ArrayList<>();
        for (final List<T> list : items) {
            flattenedList.addAll(list);
        }

        log.info("Executing batch with " + flattenedList.size() + " items in listItemWriter");
<<<<<<< HEAD
        log.info(flattenedList.toString());
=======
>>>>>>> 589cb3d (날씨 정보 조회 배치 2)
        delegate.write(flattenedList);
    }

    @Override
    public void afterPropertiesSet(){
        Assert.notNull(delegate, "A delegate for each item is required.");
    }

    @Override
    public void open(final ExecutionContext executionContext) throws ItemStreamException {
        if (delegate instanceof ItemStream) {
            ((ItemStream) delegate).open(executionContext);
        }
    }

    @Override
    public void update(final ExecutionContext executionContext) throws ItemStreamException {
        if (delegate instanceof ItemStream) {
            ((ItemStream) delegate).update(executionContext);
        }
    }

    @Override
    public void close() throws ItemStreamException {
        if (delegate instanceof ItemStream) {
            ((ItemStream) delegate).close();
        }
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 589cb3d (날씨 정보 조회 배치 2)
