FROM openjdk:8
ADD target/attendance.jar attendance.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","attendance.jar"]
