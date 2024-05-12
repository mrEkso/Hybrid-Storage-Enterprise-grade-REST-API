# Use the official Maven image as the base image
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and any other necessary configuration files
COPY ./pom.xml .

# Copy the source code
COPY src ./src

# Instead of building the jar, we just ensure that dependencies are downloaded
RUN mvn dependency:go-offline

# Command to run the application using Maven, enabling hot reloading with DevTools
CMD ["mvn", "spring-boot:run"]
