# syntax=docker/dockerfile:1

##############################
# 1단계: 빌드 환경 (Builder Stage)
##############################
FROM gradle:8.11.1-jdk21 AS builder

# 필요한 패키지 설치
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
      bash \
      curl \
      zip && \
    rm -rf /var/lib/apt/lists/*

# timezone 설정
ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 작업 디렉토리 설정
WORKDIR /app

# 프로젝트 전체 복사 (루트 디렉토리 전체를 빌드 컨텍스트로 사용)
COPY . .

# Gradle Wrapper 실행 권한 추가
RUN chmod +x ./gradlew

# 종속성 캐시 활용을 위한 의존성 다운로드 (실패해도 계속 진행)
RUN ./gradlew --no-daemon dependencies || true

# 빌드할 모듈 지정 (예: eureka-server, api-gateway, order-service 등)
# 기본값은 eureka-server. 다른 모듈로 빌드하려면 빌드 시 --build-arg MODULE=원하는모듈명을 지정
ARG MODULE=eureka-server
RUN ./gradlew :$MODULE:clean :$MODULE:bootJar --no-daemon

##############################
# 2단계: 실행 환경 (Runtime Stage)
##############################
FROM arm64v8/openjdk:21-jdk-slim

WORKDIR /app

# 실행할 프로파일 지정 (빌드 시 --build-arg ENVIRONMENT=프로파일명)
ARG ENVIRONMENT
ENV SPRING_PROFILES_ACTIVE=${ENVIRONMENT}

# 대상 모듈 지정 (빌드 시 사용한 모듈과 동일해야 함)
ARG MODULE=eureka-server
# 빌드 산출물(JAR 파일) 복사
COPY --from=builder /app/$MODULE/build/libs/*.jar app.jar

# 해당 모듈의 기본 포트 설정 (예: eureka-server는 8761)
ARG PORT=8761
EXPOSE ${PORT}

# 애플리케이션 실행
ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "app.jar"]
