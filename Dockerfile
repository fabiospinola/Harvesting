FROM alpine

# Add package manager and install dependencies
RUN apk add --no-cache curl

RUN apk add openjdk17

CMD ["--net=host"]

# Add your application
COPY target/Webscraper.jar /Webscraper.jar

# Expose port 8080
EXPOSE 8080:8080

# Run your application
CMD ["java", "-jar", "/Webscraper.jar"]