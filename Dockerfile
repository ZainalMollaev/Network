FROM openjdk:20 AS java
COPY /target/Network-1.0-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "Network-1.0-SNAPSHOT.jar"]
