# ── Stage 1: Build ──────────────────────────────────────
# Uses Maven + JDK 17 to compile and package the app
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml first — Docker caches this layer separately.
# If only source code changes (not pom.xml), Maven dependencies
# won't be re-downloaded on rebuild. Smart caching!
COPY pom.xml .
RUN mvn dependency:go-offline -q

# Copy source code and build the JAR
COPY src ./src
RUN mvn package -DskipTests -q

# ── Stage 2: Run ────────────────────────────────────────
# Much smaller image — only the JRE, not the full JDK + Maven
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy only the built JAR from Stage 1
COPY --from=build /app/target/java-webapp-1.0.0.jar app.jar

# Expose port 8080 (Spring Boot default)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
