FROM openjdk:8-jdk-alpine
EXPOSE 8080
COPY target/similarity-service-1.0-SNAPSHOT.jar /tmp
ENTRYPOINT ["java","-jar","/tmp/similarity-service-1.0-SNAPSHOT.jar"]