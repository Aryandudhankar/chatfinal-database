# Use official OpenJDK image
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Grant execute permissions to mvnw (in case it’s not already)
RUN chmod +x mvnw

# Build the project
RUN ./mvnw clean install

# Run the jar file
CMD ["java", "-jar", "target/chatfinal-0.0.1-SNAPSHOT.jar"]
