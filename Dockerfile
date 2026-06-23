FROM eclipse-temurin:25-jdk
ADD build/libs/ProjectRPG-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT java -jar -server -XshowSettings:vm -XX:MaxRAMPercentage=75.0 ProjectRPG-0.0.1-SNAPSHOT.jar
