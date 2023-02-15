package com.leehyeonmin34.weather_reminder.global.test_config;

import com.leehyeonmin34.weather_reminder.global.config.TestProfile;
import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.Ignore;
import org.junit.jupiter.api.TestInstance;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.sql.DataSource;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

/*
    Batch 테스트에서 사용할 기능들을 제공하는 추상 클래스다.
    구현 클래스에서 launchJob으로 배치를 실행하면 JobExecution을 리턴하진 않고(해당 기능이 허용되지 않음),
    jobExecution 필드를 통해 결과에 접근할 수 있다.
 */
@Ignore
@SpringBootTest
//@SpringBatchTest
@EnableBatchProcessing
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@EnableAutoConfiguration
@ActiveProfiles(TestProfile.TEST)
public abstract class BatchTestSupport {

    @Autowired
    JobLauncher jobLauncher;

    // 테스트할 job은 구현클래스에서 지정하는데,
    // 공통 메서드에 job이 필요한 경우가 있어 getJob 추상 메서드를 통해 job을 구한다.
    protected abstract Job getJob();

    protected JobExecution jobExecution = null;

    @Autowired
    protected EntityManagerFactory entityManagerFactory;

    @Autowired
    protected JobExplorer jobExplorer;

    private EntityManager entityManager = null;

    private JPAQueryFactory query = null;

    protected void launchJob(Job job) throws Exception {
        launchJob(job, getUniqueJobParameters(job));
    }

    protected void launchJob(Job job, JobParameters jobParameters) throws Exception {
        this.jobExecution = jobLauncher.run(job, jobParameters);
    }

    private JobParameters getUniqueJobParameters(Job job){
        return new JobParametersBuilder(jobExplorer) // jobExplorer - 메타 테이블에 대한 read only 쿼리 기능을 위한 인터페이스, runId 조회 가능
                .getNextJobParameters(job)
                .toJobParameters();
    }

    protected void thenBatchCompleted(){
        then(BatchStatus.COMPLETED).isEqualTo(jobExecution.getStatus());
    }

    protected void thenBatchStatus(BatchStatus batchStatus){
        then(batchStatus).isEqualTo(jobExecution.getStatus());
    }


    // 테스트를 위한 데이터 생성, 삭제.
    protected <T> T save(T entity){
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(entity);
        transaction.commit();
        entityManager.clear();
        return entity;
    }

    protected <T> List<T> saveAll(List<T> entities){
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        for (T entity: entities){
            entityManager.persist(entity);
        }
        entityTransaction.commit();
        entityManager.clear();

        return entities;
    }

    protected <T> void deleteAll(EntityPath<T> path){
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        query.delete(path).execute();
        transaction.commit();
    }

    protected <T> void delete(EntityPath<T> path) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        query.delete(path).execute();
        transaction.commit();
    }
}
