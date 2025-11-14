# Use Eclipse Temurin (OpenJDK 17) — official replacement for deprecated openjdk images
FROM eclipse-temurin:17-jdk-jammy

# Set working directory inside the container
WORKDIR /app

# Copy the JAR file from target folder to container
COPY target/project-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Optional: limit memory usage (Render/Heroku friendly)
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Run the Spring Boot application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
