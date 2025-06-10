
# ---------- STAGE 1: Build the application ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
COPY src ./src

RUN ./mvnw dependency:go-offline
RUN ./mvnw clean package -DskipTests

# ---------- STAGE 2: Build the application ----------
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy only the built jar from the builder stage
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]