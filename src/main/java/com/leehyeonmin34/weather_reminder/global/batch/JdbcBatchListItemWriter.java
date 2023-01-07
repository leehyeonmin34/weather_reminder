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
}
