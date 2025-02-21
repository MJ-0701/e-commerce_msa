plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.2.2" // 3.2.x 버전으로 변경
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Cloud Gateway
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")

    // Spring WebFlux
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // Jackson for Kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Spring Security (선택 사항)
//    implementation("org.springframework.boot:spring-boot-starter-security")

    // Eureka Client (선택 사항)
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    // Spring Boot Actuator (모니터링)
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // Resilience4j (서킷 브레이커, 선택 사항)
    implementation("io.github.resilience4j:resilience4j-spring-boot3")

    // Test dependencies
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

//    // Spring Framework 버전을 강제 지정해서 HttpHeaders.headerSet() 메서드가 제공되는 버전을 사용하도록 함.
//    constraints {
//        implementation("org.springframework:spring-web:6.0.20") {
//            because("Ensure HttpHeaders.headerSet() is available")
//        }
//    }
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.0") // Spring Cloud 2023.0.x 릴리스
    }
}
