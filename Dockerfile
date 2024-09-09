FROM openjdk:17-jdk-alpine
COPY target/vintage-0.0.1-SNAPSHOT.jar vintage-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/vintage-0.0.1-SNAPSHOT.jar"]