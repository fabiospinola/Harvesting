RUN openjdk:lastest
COPY target/ /harvest
WORKDIR /harvest
ENTRYPOINT java -jar Webscraping_f4-1.0-SNAPSHOT.jar