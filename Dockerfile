FROM maven:3.6.3 AS maven
LABEL MAINTAINER="Miguel Diaz"

WORKDIR /usr/src/app
COPY . /usr/src/app

RUN mvn clean package

FROM openjdk:11-jre

ARG JAR_FILE=albomarvel.jar
WORKDIR /opt/app
COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app/
ENTRYPOINT ["java","-jar","albomarvel.jar"]