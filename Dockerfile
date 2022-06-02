FROM openjdk:11

EXPOSE 8080

COPY build/libs/AlfaBankTestTask-0.0.1-SNAPSHOT.jar ./app/AlfaBankTestTask-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "./app/AlfaBankTestTask-0.0.1-SNAPSHOT.jar"]