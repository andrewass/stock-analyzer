# ======================
# Stage 1: Cache Gradle dependencies
# ======================
FROM gradle:9.1.0-jdk21 AS cache
WORKDIR /home/gradle/app
ENV GRADLE_USER_HOME=/home/gradle/.gradle

# Copy only files needed for dependency resolution
COPY build.gradle.kts settings.gradle.kts gradle.properties ./

# Run a task to download dependencies without building full app
RUN gradle build --no-daemon -x test || return 0

# ======================
# Stage 2: Build Application
# ======================
FROM gradle:9.1.0-jdk21 AS build
WORKDIR /home/gradle/app
ENV GRADLE_USER_HOME=/home/gradle/.gradle

# Copy Gradle cache from previous stage
COPY --from=cache /home/gradle/.gradle /home/gradle/.gradle

# Copy all source code
COPY --chown=gradle:gradle . .

# Build fat jar
RUN gradle clean assemble --no-daemon

# ======================
# Stage 3: Distroless Runtime
# ======================
FROM gcr.io/distroless/java21:nonroot
WORKDIR /app

# Copy fat jar into container
COPY --from=build /home/gradle/app/build/libs/*-all.jar app.jar

EXPOSE 8088

# Set nonroot user
USER nonroot

# Run the app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
