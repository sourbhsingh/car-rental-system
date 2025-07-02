# Stage 1: Build the Java application
# We use OpenJDK 21 because your pom.xml specifies Java 21.
# The '-jdk-slim' tag means it includes the Java Development Kit (JDK)
# which is needed to compile your code, and it's a slim version to save space.
FROM openjdk:21-jdk-slim AS build

# Set the working directory inside this temporary Docker container.
# All commands after this will run inside /app.
WORKDIR /app

# Copy your Maven project files (pom.xml and the Maven wrapper scripts).
# This is done first so Docker can cache these layers. If only your source
# code changes, Docker won't need to re-download all Maven dependencies.
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Copy all your project's source code into the container.
COPY src ./src

# Build your Spring Boot application into an executable JAR file.
# './mvnw' uses the Maven wrapper script to ensure correct Maven version.
# 'clean package' cleans previous builds and packages the new one.
# '-DskipTests' tells Maven to skip running tests, which speeds up the build for deployment.
RUN ./mvnw clean package -DskipTests

# Stage 2: Create a lightweight image to run the application
# We switch to a smaller image that only has the Java Runtime Environment (JRE).
# The '-jre-slim' tag means it's even smaller than the JDK version,
# as it only needs to run the compiled Java code, not compile it.
FROM openjdk:21-jre-slim

# Set the working directory for the final runtime container.
WORKDIR /app

# Copy the executable JAR file from the 'build' stage (the first stage).
# The JAR name is derived from your pom.xml's artifactId and version.
# 'app.jar' is a common convention for the final runnable JAR inside the container.
COPY --from=build /app/target/car-rental-system-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot application listens on.
# By default, Spring Boot apps run on port 8080.
EXPOSE 8080

# This is the command that will be executed when the Docker container starts.
# It tells Java to run your packaged Spring Boot application.
ENTRYPOINT ["java", "-jar", "app.jar"]
