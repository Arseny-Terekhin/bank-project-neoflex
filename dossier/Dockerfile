FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /opt/app
ENV MAVEN_CONFIG=/root/.m2
COPY pom.xml ./
COPY ./src ./src
RUN mvn  clean install package -DskipTests -Dmaven.repo.local=/root/.m2/repository

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /opt/app
COPY --from=build /opt/app/target/*.jar app.jar

EXPOSE 8085
ENTRYPOINT ["java", "-jar", "app.jar"]
