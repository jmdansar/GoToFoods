# Build stage
FROM eclipse-temurin:17-jdk AS build

WORKDIR /workspace

COPY mvnw pom.xml ./
COPY .mvn .mvn

COPY src src
COPY 24405.xml 24405.xml

RUN ./mvnw -q -DskipTests package

# Runtime stage
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /workspace/target/menu-api-0.0.1-SNAPSHOT.jar /app/app.jar
COPY --from=build /workspace/24405.xml /app/24405.xml

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
