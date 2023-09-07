# Base image
FROM openjdk:11-jdk-slim
ARG JAR_FILE=build/libs/*.jar

# 애플리케이션 빌드 및 JAR 파일 복사
WORKDIR /app
COPY $JAR_FILE /app/connect-back-0.0.1-SNAPSHOT.jar

# 애플리케이션 실행
CMD ["java","-Dspring.profiles.active=prod" , "-jar", "/app/connect-back-0.0.1-SNAPSHOT.jar"]