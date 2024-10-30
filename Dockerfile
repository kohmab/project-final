FROM eclipse-temurin:17

EXPOSE 8080

#RUN mvn clean install -P prod

COPY target/jira-1.0.jar jira.jar

ENTRYPOINT ["java", "-jar", "jira.jar"]