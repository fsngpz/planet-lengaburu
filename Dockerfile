# Use Temurin 21 JDK for the build stage
FROM eclipse-temurin:21-jdk AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the application code
COPY . .

# Build the application with Gradle
RUN ./gradlew build --no-daemon

# Use a lightweight Temurin 21 JRE image for the runtime environment
FROM eclipse-temurin:21-jre

# Set the working directory inside the container for the runtime environment
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Specify the command to run the app
CMD ["java", "-jar", "app.jar"]
