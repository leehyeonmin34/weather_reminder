# 날씨 알리미 
사용자가 미리 설정한 날씨 조건을 충족하면 알림을 보내주는 서비스입니다.
(ex. "오늘 오후에 비가 오니까 우산을 챙겨가세요")



![A4 - 27 (3)](https://user-images.githubusercontent.com/66104031/220347959-40b08fdf-b2f1-4962-9238-530c42558395.jpg)


# 프로젝트의 주요 관심사
- 외부 API 활용 (기상청 공공API)
- 스레드를 할용한 비동기 처리
- 메시지큐 활용
- SpringBatch를 활용한 배치 작업

## 프로젝트 구조
<img width="1229" alt="스크린샷 2023-06-25 오후 9 58 08" src="https://github.com/leehyeonmin34/weather_reminder/assets/66104031/3ce58cfc-6d60-44ac-8a45-8eaaf2b3ca01">


- 사용자가 알림을 설정할 수 있게 하는 API와 알림을 전송하기 위한 2개의 배치로 구성되어있습니다.
- **<span style="color:#228BE6" > [파란색] </span>** 오전 5시엔 공공API에 요청해 오늘 지역/시간별 날씨 데이터를 저장합니다.
- **<span style="color:#EFB23F" > [노란색] </span>** 매 10분 간격엔, 해당 시간으로 알림을 설정한 사용자들을 조회해, 해당 사용자들이 설정한 알림 설정에 맞게 메시지를 생성 혹은 미리 만들어진 메시지를 조회해 메시지큐에 넣습니다. 메시지큐에 메시지가 있음을 확인한 모니터링 스레드는 스레드풀을 생성해 사용자에게 메시지를 전송합니다. 이 때 메시지큐는 코드수준으로 직접 구현되었습니다.

## [<U>📓 각종 문제해결 등 중요한 내용은 위키를 확인해주세요 !</U>](https://github.com/leehyeonmin34/weather_reminder/wiki)

### 제공 기능
- 비 알림
- 더운 날 알림
  - 조건 온도 설정
- 추운 날 알림
  - 조건 온도 설정
- 알림 지역 편집
- 알림 시간 설정
- 알림 n일 동안 끄기

### 사용 기술
Spring Boot, Spring Batch, Spring Data JPA, MySQL, Redis, Docker, nginx, Naver Cloud Platform



<br />

# ERD
<img width="770" alt="스크린샷 2023-02-21 오후 9 47 08" src="https://user-images.githubusercontent.com/66104031/220348674-82093483-a0c7-48c2-baad-4dd5d24a41b3.png">

 
