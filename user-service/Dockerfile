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

RUN ./gradlew :user-service:clean :user-service:bootJar --no-daemon

##############################
# Runtime Stage
##############################
FROM arm64v8/openjdk:21-jdk-slim

WORKDIR /app

ARG ENVIRONMENT
ENV SPRING_PROFILES_ACTIVE=${ENVIRONMENT}

COPY --from=builder /app/user-service/build/libs/*.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-Dspring.profiles.active=${ENVIRONMENT}", "-jar", "app.jar"]
