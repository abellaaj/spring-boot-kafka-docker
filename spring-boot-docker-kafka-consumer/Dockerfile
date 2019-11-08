FROM openjdk:8-jre-alpine
VOLUME /tmp
ARG JAR_FILE=target/spring-boot-with-kafka-consumer-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} /consumer.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/consumer.jar"]
