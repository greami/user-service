FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /workspace

COPY pom.xml .
COPY domain/pom.xml domain/pom.xml
COPY application/pom.xml application/pom.xml
COPY adapter/pom.xml adapter/pom.xml
COPY domain/src domain/src
COPY application/src application/src
COPY adapter/src adapter/src

RUN mvn -pl adapter -am clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /workspace/adapter/target/adapter-0.1.0-SNAPSHOT.jar /app/user-service.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/user-service.jar"]
