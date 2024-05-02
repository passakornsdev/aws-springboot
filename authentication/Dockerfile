FROM openjdk:21-slim-bullseye

RUN mkdir -p /home/authentication/
WORKDIR /home/authentication/

COPY ./target/authentication-0.0.1-SNAPSHOT.jar /home/authentication/authentication-0.0.1-SNAPSHOT.jar

CMD ["java","-jar", "authentication-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=docker"]
