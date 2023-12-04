FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY ./build/libs/exchangetransactions-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod", "/app.jar"]