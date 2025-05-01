FROM openjdk:17-jdk
WORKDIR /app
COPY target/simplewebapp-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]