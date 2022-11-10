FROM amazoncorretto:19

RUN mkdir -p org/harvesting/output

COPY out/artifacts/Web_scraping_jar /org/harvesting

WORKDIR /org/harvesting

ENTRYPOINT java -jar Web_scraping.jar