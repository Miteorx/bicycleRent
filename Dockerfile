FROM openjdk:17

ARG JAR_FILE=./target/*.jar
WORKDIR /etc/java
COPY ${JAR_FILE} app.jar

EXPOSE 8080/tcp
ENTRYPOINT ["java", "-jar", "app.jar"]