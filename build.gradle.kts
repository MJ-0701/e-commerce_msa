plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25" apply false
    id("org.springframework.boot") version "3.2.2" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
    kotlin("plugin.jpa") version "1.9.25" apply false
    kotlin("kapt") version "1.9.25"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.25"

}

allprojects {
    apply(plugin = "kotlin")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "io.spring.dependency-management")

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

    kotlin {
        compilerOptions {
            freeCompilerArgs.addAll("-Xjsr305=strict")
        }
    }
}

val queryDslModule = listOf(
    project("order-service"),
    project("product-service"),
    project("user-service"),
    project("payment-service"),
    project("event-server"),

)

configure(queryDslModule) {
    apply {
        plugin("org.jetbrains.kotlin.plugin.jpa")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("kotlin-kapt")

    }

    allOpen {
        annotation("jakarta.persistence.Inheritance")
        annotation("jakarta.persistence.Entity")
        annotation("jakarta.persistence.Embeddable")
        annotation("jakarta.persistence.MappedSuperclass")
    }

    dependencies {
        // JPA
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")

        // QueryDsl 추가
        implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
        implementation("com.querydsl:querydsl-apt:5.0.0:jakarta")
        implementation("com.querydsl:querydsl-sql:5.0.0")
        implementation("com.blazebit:blaze-persistence-integration-querydsl-expressions-jakarta:1.6.11")
        implementation("jakarta.persistence:jakarta.persistence-api")
        implementation("jakarta.annotation:jakarta.annotation-api")

        // 캐시서버 redis
//        implementation("org.springframework.boot:spring-boot-starter-data-redis:3.4.3")


        // QueryDsl
        kapt("org.springframework.boot:spring-boot-configuration-processor")
        kapt("com.querydsl:querydsl-jpa:5.0.0:jakarta")
        kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
        kapt("com.querydsl:querydsl-sql:5.0.0")
        kapt("jakarta.persistence:jakarta.persistence-api")
        kapt("jakarta.annotation:jakarta.annotation-api")



        kapt(group = "com.querydsl", name = "querydsl-apt", classifier = "jpa")
        sourceSets.main {
            kotlin.srcDir(project.layout.buildDirectory.dir("generated/source/kapt").get().asFile.path)
            }
        }

        kapt {
            correctErrorTypes = true
        }
}

val messageQueueProject = listOf(
    project("order-service"),
    project("payment-service"),
    project("user-service"),
    project("event-server"),
    project("worker-server"),
)

configure(messageQueueProject) {

    apply {
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("kotlin-kapt")
    }

    dependencies {
        // 요청 + 처리를 위한 rabbitMQ
        implementation("org.springframework.boot:spring-boot-starter-amqp")
        // event stream을 위한 kafka
        implementation("org.springframework.kafka:spring-kafka")

    }
}

val externalApiService = listOf(
    project("user-service"),
    project("payment-service"),
)

configure(externalApiService) {
    dependencies {
        // 외부서버 통신용 feign
        implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.2.0")
    }
}