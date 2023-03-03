ARG JAR_NAME
FROM eclipse-temurin:17-jre-alpine

RUN mkdir -p /usr/local/football-app
COPY target/"$JAR_NAME" /usr/local/football-app/
COPY docker/run.sh ./run.sh

RUN chmod +x ./run.sh
CMD ./run.sh