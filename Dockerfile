# syntax=docker/dockerfile:1.7

# ---- Build stage ----
    FROM maven:3.9-eclipse-temurin-21 AS build
    WORKDIR /src
    
    COPY pom.xml .
    RUN --mount=type=cache,target=/root/.m2 mvn -B -ntp -q dependency:go-offline
    
    COPY . .
    RUN --mount=type=cache,target=/root/.m2 mvn -B -ntp -DskipTests package
    
    # ---- Runtime stage (OpenShift-friendly) ----
    FROM eclipse-temurin:21-jre-alpine
    WORKDIR /app
    # allow arbitrary non-root UID (OpenShift default) to read/write
    RUN mkdir -p /app && chgrp -R 0 /app && chmod -R g+rwX /app
    COPY --from=build /src/target/*.jar /app/app.jar
    
    EXPOSE 8080
    ENTRYPOINT ["java","-XX:MaxRAMPercentage=75.0","-XX:+UseStringDeduplication","-jar","/app/app.jar"]
    