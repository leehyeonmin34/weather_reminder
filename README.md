# 날씨 알리미 
사용자가 미리 설정한 날씨 조건을 충족하면 알림을 보내주는 서비스입니다.
(ex. "오늘 오후에 비가 오니까 우산을 챙겨가세요")

![A4 - 27 (3)](https://user-images.githubusercontent.com/66104031/220347959-40b08fdf-b2f1-4962-9238-530c42558395.jpg)


### 제공 기능
- 비 알림
- 더운 날 알림
  - 조건 온도 설정
- 추운 날 알림
  - 조건 온도 설정
- 알림 지역 편집
- 알림 시간 설정
- 알림 n일 동안 끄기

<br />

# 프로젝트의 주요 관심사
- 외부 API 활용 (기상청 공공API)
- 스레드를 할용한 비동기 처리
- 메시지큐 활용
- SpringBatch를 활용한 배치 작업


## 사용 기술
Spring Boot, Spring Batch, Spring Data JPA, MySQL, Redis, Docker, nginx, Naver Cloud Platform

## 프로젝트 구조
- github hook을 받아 Jenkins에서 CI/CD를 진행합니다.
- 젠킨스와 메인서버 2개로 작동중이며, 메인서버가 docker를 통해 MySQL, Redis, 앱서버를 구동중입니다.
- Naver Cloud Platform의 서버를 사용합니다.
- Blue-green 방식으로 무중단 배포됩니다.


<br />

## 문제 해결 사례

### 비동기 메시지 큐
- [캐시와 배치, 비동기를 활용한 빠른 알림 생성](https://github.com/leehyeonmin34/weather_reminder/wiki/%EC%BA%90%EC%8B%9C%EC%99%80-%EB%B0%B0%EC%B9%98,-%EB%B9%84%EB%8F%99%EA%B8%B0%EB%A5%BC-%ED%99%9C%EC%9A%A9%ED%95%9C-%EB%B9%A0%EB%A5%B8-%EC%95%8C%EB%A6%BC-%EC%83%9D%EC%84%B1)
- [코드 수준에서 메시지 큐 구현](https://github.com/leehyeonmin34/weather_reminder/wiki/%EC%BD%94%EB%93%9C-%EC%88%98%EC%A4%80%EC%97%90%EC%84%9C-%EB%A9%94%EC%8B%9C%EC%A7%80-%ED%81%90-%EA%B5%AC%ED%98%84)

### 배치
- [Batch ItemWriter에 List 전달하기](https://github.com/leehyeonmin34/weather_reminder/wiki/Batch-ItemWriter%EC%97%90-List-%EC%A0%84%EB%8B%AC%ED%95%98%EA%B8%B0)
- [배치 인서트를 위해 JdbcBatchItemWriter를 선택](https://github.com/leehyeonmin34/weather_reminder/wiki/%EB%B0%B0%EC%B9%98-%EC%9D%B8%EC%84%9C%ED%8A%B8%EB%A5%BC-%EC%9C%84%ED%95%B4-JdbcBatchItemWriter%EB%A5%BC-%EC%84%A0%ED%83%9D)

### 테스트
- [다양한 시나리오를 고려한 TDD, BDD 유닛 테스트](https://github.com/leehyeonmin34/weather_reminder/wiki/%EB%8B%A4%EC%96%91%ED%95%9C-%EC%8B%9C%EB%82%98%EB%A6%AC%EC%98%A4%EB%A5%BC-%EA%B3%A0%EB%A0%A4%ED%95%9C-TDD,-BDD-%EC%9C%A0%EB%8B%9B-%ED%85%8C%EC%8A%A4%ED%8A%B8)
- [테스트 코드를 위한 환경 구축](https://github.com/leehyeonmin34/weather_reminder/wiki/%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%BD%94%EB%93%9C%EB%A5%BC-%EC%9C%84%ED%95%9C-%ED%99%98%EA%B2%BD-%EA%B5%AC%EC%B6%95)
- [테스트 코드를 위해 QueryDSL 활용](https://github.com/leehyeonmin34/weather_reminder/wiki/%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%BD%94%EB%93%9C%EB%A5%BC-%EC%9C%84%ED%95%B4-QueryDSL-%ED%99%9C%EC%9A%A9)

<br />

# ERD
<img width="770" alt="스크린샷 2023-02-21 오후 9 47 08" src="https://user-images.githubusercontent.com/66104031/220348674-82093483-a0c7-48c2-baad-4dd5d24a41b3.png">

 
