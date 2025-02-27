FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/syonet-app.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
