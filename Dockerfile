# Use Java 21
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy everything
COPY . .

# Change to the folder where pom.xml exists
WORKDIR /app/chatfinal

# Make mvnw executable
RUN chmod +x mvnw

# Build the app (skip tests!)
RUN ./mvnw clean install -DskipTests

# Run the app
CMD ["java", "-jar", "target/chatfinal-0.0.1-SNAPSHOT.jar"]
