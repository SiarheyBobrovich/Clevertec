FROM openjdk:17
MAINTAINER BOBROVICH

ADD src/main/resources/application-docker.yml /app/application.yml

COPY build/libs/market-0.0.1-SNAPSHOT.jar /app/market.jar
ENTRYPOINT ["java","-jar","/app/market.jar"]