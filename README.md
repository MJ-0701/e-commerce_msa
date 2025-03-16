MSA E-Commerce 백엔드 프로젝트
이 프로젝트는 Spring Boot와 Kotlin을 기반으로 구축한 MSA(마이크로서비스 아키텍처) e-commerce 백엔드 시스템입니다. 확장성, 유연성, 그리고 안정성을 고려하여 서비스 간 명확한 역할 분리와 비동기 메시지 기반 통신을 구현하였습니다.

주요 아키텍처 컴포넌트
1. Eureka Server
역할: 전체 서비스 인스턴스의 등록 및 디스커버리 제공
설명: 마이크로서비스들이 동적으로 등록되고 서로를 탐색할 수 있도록 하여, 서비스 간 부하 분산 및 장애 대응을 지원합니다.
2. API Gateway
역할: 클라이언트(프론트엔드)와 백엔드 서비스 간의 단일 진입점 역할
설명: 요청 라우팅, 인증, 요청 집계 등의 기능을 제공하며, 내부 서비스의 노출을 최소화하여 보안과 확장성을 강화합니다.
3. Event Server
역할: e-commerce 도메인 이벤트(예: 선착순 쿠폰, 상시 할인 이벤트) 관리
통신: Kafka를 이용하여 동시성 제어 및 이벤트 스트림 처리
4. Order Service
역할: 주문 관리 및 관련 비즈니스 로직 처리
설명: 주문 생성, 업데이트, 조회 등 주문 관련 모든 기능을 담당합니다.
5. Product Service
역할: 상품 정보 관리
설명: 상품 등록, 수정, 검색 등의 기능을 제공하며, 상품 관련 데이터의 무결성을 유지합니다.
6. User Service
역할: 사용자 및 회원 정보 관리
설명: 회원 가입, 인증, 프로파일 관리 등 사용자 관련 기능을 전담합니다.
7. Worker Server
역할: 비동기 작업 및 서비스 간 비동기 메시지 처리
통신: RabbitMQ를 활용하여 다른 서비스와 메시지 기반 통신 진행
설명: 시간이 많이 소요되거나 비동기적으로 처리 가능한 작업을 분산 처리하여 시스템의 응답성과 확장성을 향상시킵니다.
기술 스택
Backend Framework: Spring Boot
Programming Language: Kotlin
Service Discovery: Eureka
API Gateway: Spring Cloud Gateway (또는 Netflix Zuul)
메시지 브로커:
Kafka: 이벤트 스트림 및 동시성 제어
RabbitMQ: 비동기 작업 처리
캐시: Redis (조회 API 성능 최적화)
배포 도구: Docker, Docker Compose, Kubernetes
프로젝트 디렉터리 구조
plaintext
복사
msa-ecommerce-backend/
├── eureka-server/           # 서비스 디스커버리 서버
├── api-gateway/             # 클라이언트 요청의 진입점
├── event-server/            # 이벤트 및 프로모션 관리
├── order-service/           # 주문 처리 및 관리
├── product-service/         # 상품 정보 관리
├── user-service/            # 사용자 및 회원 정보 관리
└── worker-server/           # 비동기 작업 처리 (RabbitMQ 통신)
배포 및 운영 전략
Docker & Docker Compose
Docker: 각 마이크로서비스를 컨테이너화하여 일관된 환경에서 실행
Docker Compose: 로컬 개발 및 테스트 환경에서 서비스 간 통신 및 전체 시스템 통합 테스트
Kubernetes
역할: 클러스터 환경에서의 확장성, 고가용성 및 자동화된 배포 관리
구성: 각 서비스의 Deployment와 Service 리소스로 구성하여, 오토스케일링과 롤링 업데이트 지원
개발 및 배포 가이드
프로젝트 클론 및 빌드

bash
복사
git clone https://github.com/your-repo/msa-ecommerce-backend.git
cd msa-ecommerce-backend
./gradlew build
로컬 환경에서 실행

bash
복사
docker-compose up --build
Kubernetes 클러스터에 배포

bash
복사
kubectl apply -f k8s/
주요 특징 및 강점
서비스 간 역할 분리: 각 도메인별 전담 서비스로 책임 분리 및 유지보수 용이
비동기 메시징: Kafka와 RabbitMQ를 통한 효율적인 이벤트 및 비동기 작업 처리
캐싱 전략: Redis를 활용하여 API 조회 성능 및 응답 속도 개선
컨테이너 기반 배포: Docker 및 Kubernetes를 통한 자동화된 배포 및 스케일링으로 운영 효율성 강화
