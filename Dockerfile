FROM eclipse-temurin:17

EXPOSE 8080

COPY resources ./resources
COPY target/jira-1.0.jar /jira.jar

ENTRYPOINT ["java", "-jar",  "jira.jar"]
# По-видимому более правильно так:
#ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "jira.jar"]
# но чтобы проще было читать логи из докера оставил первый вариант.
