# STEP 1: Use a lightweight official JDK image to run the app
FROM eclipse-temurin:21-jdk-alpine

# STEP 2: Set the working directory inside the container
WORKDIR /app

# STEP 3: Copy the JAR from your local target folder into the container
COPY target/gestionlicenciasconducir-0.0.1-SNAPSHOT.jar app.jar

# STEP 4: Expose the port that Spring Boot uses (default is 8080)
EXPOSE 8080

# STEP 5: Define the startup command
ENTRYPOINT ["java", "-jar", "app.jar"]

