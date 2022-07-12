FROM openjdk:8-jre
ARG test
COPY test app.jar
EXPOSE 10086
ENTRYPOINT ["java","-jar","/app.jar", "--spring.profiles.active=test"]
