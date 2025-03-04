pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "commerce"

include("api-gateway")
include("eureka-server")
include("order-service")
include("product-service")
include("user-service")
include("common")
include("event-server")
include("payment-service")
include("worker-server")