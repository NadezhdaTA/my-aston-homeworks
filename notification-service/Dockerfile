FROM eclipse-temurin:21-jdk-jammy
VOLUME /tmp
COPY target/notification-service*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]