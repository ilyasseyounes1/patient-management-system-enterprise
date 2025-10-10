# Use Maven image with Java 21 (Eclipse Temurin) as the build environment
FROM maven:3.9.9-eclipse-temurin-21 AS build

# Set the working directory inside the container to /app
WORKDIR /app

# Copy only the pom.xml first to leverage Docker cache for dependencies
COPY pom.xml .

# Pre-download all Maven dependencies (offline) to speed up build | -B (batch mode)
                                                                    #Runs Maven in batch mode, which is non-interactive.
RUN mvn dependency:go-offline -B

# Copy the entire source code into the container
COPY src ./src

# Build the project and create a JAR file, skipping tests
RUN mvn clean package -DskipTests

# Use a smaller base image with just Java 21 to run the application
FROM openjdk:21-jdk AS runner

# Set working directory for the runtime image
WORKDIR /app

# Copy the built JAR file from the build stage into the runtime image
COPY --from=build ./app/target/patient-service-0.0.1-SNAPSHOT.jar ./app.jar

# Expose port 4000 so the container can be accessed from outside
EXPOSE 4000

# Set the command to run the application when the container starts
ENTRYPOINT ["java","-jar","app.jar"]
