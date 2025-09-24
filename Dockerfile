# Stage 1: Cache Gradle dependencies
FROM gradle:latest AS cache
RUN mkdir -p /home/gradle/cache_home
ENV GRADLE_USER_HOME /home/gradle/cache_home
COPY build.gradle.* gradle.properties /home/gradle/app/
WORKDIR /home/gradle/app
RUN gradle clean build -i --stacktrace

# Stage 2: Build Application
FROM gradle:latest AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

# Stage 3
FROM eclipse-temurin:21-jre-alpine
COPY --from=build /home/gradle/src/build/libs/*-all.jar app.jar
EXPOSE 8088
ENTRYPOINT ["java","-jar","/app.jar"]