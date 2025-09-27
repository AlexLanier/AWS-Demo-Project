# syntax=docker/dockerfile:1.7

# ---- Build stage ----
FROM maven:3.9.8-eclipse-temurin-17 as build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -B dependency:go-offline
COPY src ./src
#RUN mvn -q -B package -DskipTests
RUN mvn -q -B package
# ---- Runtime stage ----
FROM gcr.io/distroless/java17-debian12
WORKDIR /app
COPY --from=build /app/target/*-SNAPSHOT.jar /app/app.jar
USER nonroot:nonroot
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75.0 -XX:+UseStringDeduplication"
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
    