# 날씨 알리미 
사용자가 미리 설정한 날씨 조건을 충족하면 알림을 보내주는 서비스입니다.
(ex. "오늘 오후에 비가 오니까 우산을 챙겨가세요")


![A4 - 27 (3)](https://user-images.githubusercontent.com/66104031/220347959-40b08fdf-b2f1-4962-9238-530c42558395.jpg)


# 프로젝트의 주요 관심사
- 외부 API 활용 (기상청 공공API)
- 스레드를 할용한 비동기 처리
- 메시지큐 활용
- SpringBatch를 활용한 배치 작업
- 체계적인 단위/통합 테스트 및 테스트 설정
- 쿼리 최적화
- git-flow 브랜치 관리 전략 & 이슈 단위 브랜치 생성

<br />

## 프로젝트 구조
<img width="1213" alt="image" src="https://github.com/leehyeonmin34/dambae200/assets/66104031/f261a052-a325-4471-bb77-3916ce78acf9">

이 앱은 사용자가 알림을 설정할 수 있게 하는 API와 알림을 전송하기 위한 2개의 배치로 구성되어있습니다.

### 배치
**[파란색 배치]** 오전 5시엔 공공API에 요청해 오늘 지역/시간별 날씨 데이터를 저장합니다.

**[노란색 배치]** 매 10분 간격엔, 해당 시간으로 알림을 설정한 사용자들을 조회해, 해당 사용자들이 설정한 알림 설정에 맞게 메시지를 생성합니다. 이 메시지는 캐시와 DB에 저장되어 같은 알림조건의 사용자에게 메시지를 보낼때 재활용됩니다. 생성된 메시지는 전송되기 위해 메시지큐에 삽입됩니다.  메시지큐에 메시지가 있음을 확인한 모니터링 스레드는 스레드풀을 생성해 사용자에게 메시지를 전송합니다. 이 때 메시지큐는 코드수준으로 직접 구현되었습니다.

### 사용자 API
- 비 알림
- 더운 날 알림
  - 조건 온도 설정
- 추운 날 알림
  - 조건 온도 설정
- 알림 지역 편집
- 알림 시간 설정
- 알림 n일 동안 끄기

### 사용 기술
- Spring Boot 
- Spring Batch
- Spring Data JPA
- QueryDSL
- MySQL
- Redis
- nginx
- Docker
- Jenkins
- Naver Cloud Platform

# 문제 해결

## 비동기 처리
[🔗 캐시와 배치, 비동기를 활용해 667% 빠른 알림 생성 🔥](https://velog.io/@leehyeonmin34/weather-reminder-noti-generation)

[🔗 코드 수준에서 메시지 큐 구현 🔥](https://velog.io/@leehyeonmin34/weather-reminder-message-q-as-code)


<br />

## 테스트
[🔗 @ParemeterizedTest를 적극활용해 다양한 시나리오를 고려한 BDD 유닛 테스트 🔥](https://velog.io/@leehyeonmin34/parameterized-test)

[🔗 테스트 종류별로 설정을 일관되게 관리 🔥](https://github.com/leehyeonmin34/weather_reminder/wiki/%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%BD%94%EB%93%9C%EB%A5%BC-%EC%9C%84%ED%95%9C-%ED%99%98%EA%B2%BD-%EA%B5%AC%EC%B6%95)

[🔗 테스트 롤백용 쿼리를 위해 QueryDSL 활용](https://velog.io/@leehyeonmin34/weather-reminder-querydsl)

<br />


## 쿼리 최적화

[🔗 핵심 조회쿼리에 복합인덱스를 설정해 조회성능 900% 개선(쿼리 실행계획 확인) 🔥](https://velog.io/@leehyeonmin34/weather-reminder-multi-column-index)

[🔗 deleteAll 대신 deleteAllInBatch를 사용해 2N개의 쿼리를 2개로](https://velog.io/@leehyeonmin34/weather-info-delete-all-in-batchC)

[🔗 saveAll이 배치 인서트를 실행하도록 설정해 N개의 쿼리를 1개로](https://velog.io/@leehyeonmin34/weather-reminder-saveall-to-batch-insert)

<br />

## 기타

[🔗 협업 경험을 시뮬레이션하기 위해 git-flow 브랜치 관리, 이슈 단위로 커밋](https://velog.io/@leehyeonmin34/weather-reminder-git-flow)

[🔗 DB 용량을 줄이기 위해 Converter로 Java 객체를 DB 칼럼에 맵핑](https://velog.io/@leehyeonmin34/weather-reminder-converter)

[🔗 배치 인서트를 위해 JdbcBatchItemWriter를 선택](https://github.com/leehyeonmin34/weather_reminder/wiki/%EB%B0%B0%EC%B9%98-%EC%9D%B8%EC%84%9C%ED%8A%B8%EB%A5%BC-%EC%9C%84%ED%95%B4-JdbcBatchItemWriter%EB%A5%BC-%EC%84%A0%ED%83%9D)

[🔗 SpringBatch ItemWriter에 List 전달하기](https://velog.io/@leehyeonmin34/weather-reminder-nested-list-to-item-writer)

[🔗 @Retryable을 활용해 API 요청 타임아웃 및 재시도 설정](https://velog.io/@leehyeonmin34/weather-reminder-retryable-rest-template)


<br />

# ERD
<img width="770" alt="스크린샷 2023-02-21 오후 9 47 08" src="https://user-images.githubusercontent.com/66104031/220348674-82093483-a0c7-48c2-baad-4dd5d24a41b3.png">

 
