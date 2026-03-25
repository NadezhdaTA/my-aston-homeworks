FROM eclipse-temurin:21-jdk-jammy
VOLUME /tmp
COPY target/aston-config-server*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

