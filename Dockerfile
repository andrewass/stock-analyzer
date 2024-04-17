
FROM eclipse-temurin:21-jre-alpine

ARG JAR_FILE=./build/libs/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8088

ENTRYPOINT ["java","-jar","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005","/app.jar"]