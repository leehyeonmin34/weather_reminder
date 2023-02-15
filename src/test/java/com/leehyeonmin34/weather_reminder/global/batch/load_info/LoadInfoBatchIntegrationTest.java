package com.leehyeonmin34.weather_reminder.global.batch.load_info;

import com.leehyeonmin34.weather_reminder.global.test_config.BatchTestSupport;
import org.junit.Ignore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;

@Ignore // 테스트를 위해 모든 지역 날씨정보에 대한 API를 요청할 필요까진 없을 것 같음. 시간 체크용으로만 사용
public class LoadInfoBatchIntegrationTest extends BatchTestSupport {

    private final static String NORMAL = LoadInfoBatch.JOB_NAME; // 44190 mills for 4 api calls
    private final static String ASYNC = AsyncLoadInfoBatch.JOB_NAME; // 42077 mills for 4 api calls
    private final static String MULTI_THREAD = MultiThreadLoadInfoBatch.JOB_NAME; // 42077 mills for 4 api calls
    private final static String JOB_NAME = MULTI_THREAD; // 6785 25756 4603 mills for 4 api calls, 11714 mills

    @Autowired
    @Qualifier(JOB_NAME + "_loadInfoJob")
    private Job loadInfoJob;

    @Override
    protected Job getJob() {
        return loadInfoJob;
    }

    @Test
    @DisplayName("API 정보 조회 배치 테스트")
    public void loadInfoBatchTest() throws Exception {

        // WHEN
        launchJob(loadInfoJob);

        // THEN
        thenBatchCompleted();
    }

}