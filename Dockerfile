FROM maven:3.8.3 AS build
COPY . /app
WORKDIR /app
RUN mvn -Dmaven.test.skip=true -f /app/pom.xml clean package

FROM openjdk:17-jdk-alpine
RUN mkdir "ftpuser"
COPY --from=build /app/target/FtpServerDssm-1.0-SNAPSHOT.jar /app/FtpServerDssm-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/app/FtpServerDssm-1.0-SNAPSHOT.jar"]