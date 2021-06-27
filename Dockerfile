
FROM openjdk:11.0.7-jre-slim

ARG JAR_FILE=./build/libs/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]