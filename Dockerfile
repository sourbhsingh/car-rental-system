# Stage 1: Build the Java application
FROM openjdk:21-slim AS build

WORKDIR /app

COPY pom.xml ./
COPY mvnw ./
COPY .mvn ./.mvn
COPY src ./src

# 👇 Add this line to fix the permission issue
RUN chmod +x mvnw

# Now run the Maven build
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /app/target/car-rental-system-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
