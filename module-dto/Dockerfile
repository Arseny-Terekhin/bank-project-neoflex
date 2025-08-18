FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /opt/app
COPY pom.xml ./
COPY ./src ./src
RUN mvn clean install -DskipTests -Dmaven.repo.local=/root/.m2/repository
