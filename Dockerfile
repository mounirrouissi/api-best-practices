# Use the official OpenJDK 17 image as the base image
FROM openjdk:17-jdk-alpine
# Set the working directory in the Docker image
WORKDIR /app
# Copy the JAR file into the Docker image
COPY target/animals-app2.jar /app/app.jar
# Expose port 8080 for the application
EXPOSE 8080
# Run the applicationCMD ["java", "-jar", "/app/app.jar"]
CMD ["java", "-jar", "/app/app.jar"]