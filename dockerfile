
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

RUN useradd --system --uid 10001 appuser

USER appuser

ENTRYPOINT ["java", "-jar", "app.jar"]
