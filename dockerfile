FROM amazoncorretto:11
COPY target/*.jar test_task.jar
ENTRYPOINT ["java","-jar","/test_task.jar"]