import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktorVersion: String by project
val kotlinVersion: String by project
val kotlinxDateTimeVersion: String by project
val yahooFinanceVersion: String by project
val logbackVersion: String by project
val kodeinVersion: String by project
val jUnitVersion: String by project
val ehcacheVersion: String by project
val mockkVersion: String by project
val jedisVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.8.20"
    kotlin("plugin.serialization") version "1.8.20"
    id("com.google.cloud.tools.jib") version "3.3.1"
}

group = "stock.me"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$kotlinxDateTimeVersion")
    implementation("org.kodein.di:kodein-di-framework-ktor-server-controller-jvm:$kodeinVersion")
    implementation("com.yahoofinance-api:YahooFinanceAPI:$yahooFinanceVersion")
    implementation("org.ehcache:ehcache:$ehcacheVersion")
    implementation("redis.clients:jedis:$jedisVersion")
    implementation("io.ktor:ktor-server-cors:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-client-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-client-cio-jvm:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("io.ktor:ktor-server-test-host-jvm:$ktorVersion")
}

jib.to.image="stockanalyzer-image"

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    manifest {
        attributes("Main-Class" to "stock.me.ApplicationKt")
    }
    from(configurations.compileClasspath.get()
        .map { if (it.isDirectory) it else zipTree(it) })
}