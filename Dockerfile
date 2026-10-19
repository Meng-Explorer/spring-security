FROM eclipse-temurin:25-jdk AS builder
WORKDIR /workspace

COPY gradlew gradlew
COPY gradle gradle
COPY settings.gradle settings.gradle
COPY build.gradle build.gradle
COPY src src

RUN chmod +x gradlew && ./gradlew clean build -x test

FROM eclipse-temurin:25-jdk
WORKDIR /app

COPY --from=builder /workspace/build/libs/*.jar /app/app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
