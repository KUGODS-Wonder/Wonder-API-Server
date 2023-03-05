FROM openjdk:11-jdk

EXPOSE 8080
ADD ./build/libs/*.jar app.jar

CMD ["java", "-jar", "app.jar"]