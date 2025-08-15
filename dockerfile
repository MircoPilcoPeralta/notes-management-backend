# Ofitial Java image (light)
FROM openjdk:17-jdk-slim
# work directory
WORKDIR /app
COPY target/notes-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
# command to run the app
ENTRYPOINT ["java","-jar","app.jar"]