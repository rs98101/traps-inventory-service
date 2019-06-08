FROM maven:3-jdk-8-slim AS builder

COPY . /usr/src/mymaven
WORKDIR /usr/src/mymaven
RUN mvn clean install


FROM openjdk:8-jre-alpine
EXPOSE 8080
COPY --from=builder  /usr/src/mymaven/target/*.jar /app/inventory-service.jar
WORKDIR /app

HEALTHCHECK --interval=5m --timeout=3s \
  CMD curl -f http://localhost:8080/actuator/health

CMD java -jar /app/inventory-service.jar