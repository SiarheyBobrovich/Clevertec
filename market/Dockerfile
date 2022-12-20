FROM openjdk:17
MAINTAINER BOBROVICH

ADD src/main/resources/application.properties /app/application.properties

COPY build/libs/market-0.0.1-SNAPSHOT.jar market.jar
ENTRYPOINT ["java","-jar","/market.jar"]