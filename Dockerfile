# Alpine because it's lighter
FROM openjdk:8-jdk-alpine
MAINTAINER Ramazan Sakin <ramazansakin63@gmail.com>

# Add JAR file and run it as entrypoint
ADD target/customer-service.jar customer-service.jar
ENTRYPOINT ["java", "-jar", "customer-service.jar"]

# Expose the port
EXPOSE 8090