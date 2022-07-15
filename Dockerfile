FROM openjdk:17-alpine
COPY ./target/fitness_app-0.0.1-SNAPSHOT.jar fitness.jar
CMD ["java","-jar","fitness.jar"]
