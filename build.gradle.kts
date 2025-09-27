
plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktor)
    alias(libs.plugins.jib)
}

group = "stockcomp.client.backend"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories {
    mavenCentral()
}

dependencies {
    // --- Ktor Server ---
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.content.negotiation)

    implementation(libs.ktor.serialization.kotlinx.json)

    // --- Ktor Client ---
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)

    // --- Other runtime ---
    implementation(libs.kotlinx.datetime)
    implementation(libs.logback)
    implementation(libs.kodein)
    implementation(libs.ehcache)
    implementation(libs.jedis)

    // --- Testing ---
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.mockk)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
