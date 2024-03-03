FROM amazoncorretto:17-alpine
COPY target/*.jar dota2-match-details-app.jar

ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/dota2-match-details-app.jar"]
