#
# Build stage
#
FROM maven:3.8.5-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

#
# Package stage
#
FROM openjdk:17-oracle
COPY --from=build /home/app/target/*.jar management.jar
EXPOSE 8084
ENTRYPOINT ["java","-jar","management.jar"]