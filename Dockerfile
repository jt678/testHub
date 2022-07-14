FROM openjdk:8-jre
ARG test
COPY target/test-0.0.1-SNAPSHOT.jar /mnt
EXPOSE 8080 9090 80
ENTRYPOINT ["java","-jar","/mnt/test-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=test"]
