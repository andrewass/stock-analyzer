import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val kodeinVersion: String by project
val jUnitVersion: String by project
val kGraphqlVersion: String by project
val ehcacheVersion: String by project
val log4jVersion: String by project
val mockkVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
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
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("io.ktor:ktor-jackson:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("org.apache.logging.log4j:log4j-core:$log4jVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.1")
    implementation("org.kodein.di:kodein-di-framework-ktor-server-controller-jvm:$kodeinVersion")
    implementation("com.yahoofinance-api:YahooFinanceAPI:3.15.0")
    implementation("org.elasticsearch.client:elasticsearch-rest-high-level-client:7.15.2")
    implementation("co.elastic.clients:elasticsearch-java:7.16.2")
    implementation("org.ehcache:ehcache:$ehcacheVersion")
    implementation("com.apurebase:kgraphql:$kGraphqlVersion")
    implementation("com.apurebase:kgraphql-ktor:$kGraphqlVersion")
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
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