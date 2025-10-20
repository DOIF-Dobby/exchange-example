# STAGE 1: Gradle을 사용하여 애플리케이션 빌드
FROM gradle:8.7-jdk21-jammy AS build

WORKDIR /app

# 빌드에 필요한 파일들만 먼저 복사하여 Docker 캐시를 효율적으로 사용
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
COPY src ./src

# Gradle을 사용하여 실행 가능한 Jar 파일 생성
RUN gradle bootJar

# STAGE 2: 실제 실행을 위한 작은 이미지 생성
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# 빌드 스테이지에서 생성된 Jar 파일을 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 애플리케이션이 8080 포트를 사용함을 명시
EXPOSE 8080
ENV SERVER_PORT=8080

# 컨테이너가 시작될 때 애플리케이션 실행
ENTRYPOINT ["java", "-Dserver.port=${SERVER_PORT}", "-jar", "app.jar"]
