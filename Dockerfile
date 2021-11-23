FROM adoptopenjdk:8-jre-hotspot
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} primer-api.jar
ENTRYPOINT ["java","-jar","/primer-api.jar"]