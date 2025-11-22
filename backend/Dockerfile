
# ---------- STAGE 1: Build the application ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY backend/pom.xml .
COPY backend/mvnw .
COPY backend/mvnw.cmd .
COPY backend/.mvn .mvn
RUN ./mvnw dependency:go-offline

# copy source code and data (this busts cache whenever code changes?)
COPY backend/src ./src
COPY data src/main/resources/data

RUN ./mvnw clean package -DskipTests

# ---------- STAGE 2: Build the application ----------
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy only the built jar from the builder stage
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]