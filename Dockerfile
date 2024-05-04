# Use OpenJDK 17 as the base image for the container
FROM openjdk:17-jdk-slim as build

# Set the working directory in the Docker container
WORKDIR /app

# Copy the Maven wrapper script
COPY mvnw .
COPY .mvn ./.mvn

# Copy the Maven POM file and source code
COPY pom.xml .
COPY src ./src

# Make the Maven wrapper script executable
RUN chmod +x mvnw

# Build the application using Maven
RUN ./mvnw package -DskipTests

# Use OpenJDK 17 to run the application
FROM openjdk:17-jdk-slim

# Set the working directory in the Docker container
WORKDIR /app

# Copy the built application from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8090
EXPOSE 8090

# Set the active Spring profile
ENV SPRING_PROFILES_ACTIVE=prod

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
