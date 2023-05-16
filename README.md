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

## [<U>📓 각종 문제해결 등 중요한 내용은 위키를 확인해주세요 !</U>](https://github.com/leehyeonmin34/weather_reminder/wiki)



## 사용 기술
Spring Boot, Spring Batch, Spring Data JPA, MySQL, Redis, Docker, nginx, Naver Cloud Platform

## 프로젝트 구조
- github hook을 받아 Jenkins에서 CI/CD를 진행합니다.
- 젠킨스와 메인서버 2개로 작동중이며, 메인서버가 docker를 통해 MySQL, Redis, 앱서버를 구동중입니다.
- Naver Cloud Platform의 서버를 사용합니다.
- Blue-green 방식으로 무중단 배포됩니다.


# ERD
<img width="770" alt="스크린샷 2023-02-21 오후 9 47 08" src="https://user-images.githubusercontent.com/66104031/220348674-82093483-a0c7-48c2-baad-4dd5d24a41b3.png">

 
