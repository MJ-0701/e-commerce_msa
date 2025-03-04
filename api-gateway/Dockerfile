# syntax=docker/dockerfile:1

##############################
# Builder Stage
##############################
FROM gradle:8.11.1-jdk21 AS builder

RUN apt-get update && \
    apt-get install -y --no-install-recommends bash curl zip && \
    rm -rf /var/lib/apt/lists/*

ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew --no-daemon dependencies || true

RUN ./gradlew :api-gateway:clean :api-gateway:bootJar --no-daemon

##############################
# Runtime Stage
##############################
FROM arm64v8/openjdk:21-jdk-slim

WORKDIR /app

ARG ENVIRONMENT
ENV SPRING_PROFILES_ACTIVE=${ENVIRONMENT}

# wait-for-it 스크립트를 이미지에 복사하고 실행 권한 부여
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# 빌드 단계에서 생성한 JAR 파일 복사
COPY --from=builder /app/api-gateway/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "/wait-for-it.sh eureka-server:8761 --timeout=30 --strict && java -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -jar app.jar"]
