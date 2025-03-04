plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server") // Eureka Server 의존성
    implementation("org.springframework.boot:spring-boot-starter-actuator") // 헬스 체크 (선택 사항)
    implementation("org.jetbrains.kotlin:kotlin-reflect") // Kotlin Reflection
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.4")
    }
}
