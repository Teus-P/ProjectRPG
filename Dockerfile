FROM openjdk:17.0.2-jdk
ADD build/libs/ProjectRPG-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD java -jar ProjectRPG-0.0.1-SNAPSHOT.jar
