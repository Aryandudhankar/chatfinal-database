# Use Java 21 base image
FROM eclipse-temurin:21-jdk

# Set working directory inside container
WORKDIR /app

# Copy everything into the container
COPY . .

# Move into the actual project directory where pom.xml is
WORKDIR /app/chatfinal

# Ensure mvnw is executable
RUN chmod +x mvnw

# Build the Spring Boot app
RUN ./mvnw clean install

# Run the app
CMD ["java", "-jar", "target/chatfinal-0.0.1-SNAPSHOT.jar"]
