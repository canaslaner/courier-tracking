FROM adoptopenjdk/openjdk11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} tracking.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/tracking.jar"]