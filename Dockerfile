FROM amazoncorretto:17-alpine
COPY target/dota2-match-details-*.jar dota2-match-details-app.jar

ENTRYPOINT ["java","-Dspring.profiles.active=gke,prod","-jar","/dota2-match-details-app.jar"]
