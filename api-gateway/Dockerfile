FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY target/*.jar ap.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]