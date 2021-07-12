import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val exposedVersion: String by project
val hikariVersion: String by project
val postgreSqlVersion: String by project
val kodeinVersion: String by project
val kotlinxVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.5.10"
    kotlin("plugin.serialization") version "1.5.10"
}

group = "stock.me"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation ("io.ktor:ktor-serialization:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.2.0")
    implementation("org.kodein.di:kodein-di-framework-ktor-server-controller-jvm:$kodeinVersion")
    implementation("com.zaxxer:HikariCP:$hikariVersion")
    implementation("org.postgresql:postgresql:$postgreSqlVersion")
    implementation("com.yahoofinance-api:YahooFinanceAPI:3.15.0")
    implementation("org.elasticsearch.client:elasticsearch-rest-high-level-client:7.13.3")

    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

tasks.withType<Jar> {
    manifest {
        attributes("Main-Class" to "stock.me.ApplicationKt")
    }
    from(configurations.compileClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}