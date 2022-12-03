FROM openjdk:11-jdk

# 개발자
MAINTAINER leehyeonmin34

# 호스트(weather-reminder-node)와 공유할 폴더. 컨테이너(openjdk)의 경로(아래 값)의 폴더를,
# 호스트의 /var/lib/docker/volumnes/[hashValue]의 디렉토리에 저장하게 된다.
VOLUME /deploy/weather-reminder

# 빌드 파일을 app.jar로 복사해서 실행
ENV JAR_FILE=deploy/build/libs/demo-0.0.1-SNAPSHOT.jar
COPY $JAR_FILE app.jar
ENTRYPOINT ["java","-jar","app.jar"]