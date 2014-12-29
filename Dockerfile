FROM java:7
MAINTAINER donbonifacio

RUN mkdir -p /opt/obb-rules-api
ADD ./target/obb-rules-api.jar /opt/obb-rules-api/obb-rules-api.jar

WORKDIR /opt/obb-rules-api

EXPOSE 3000
ENV APP_ENV production

ENTRYPOINT java -jar obb-rules-api.jar
