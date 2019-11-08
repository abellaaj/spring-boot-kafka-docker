FROM openjdk:8-jre-alpine
VOLUME /tmp
ARG JAR_FILE=target/spring-boot-with-kafka-producer-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} /producer.jar
EXPOSE 9002
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/producer.jar"]
