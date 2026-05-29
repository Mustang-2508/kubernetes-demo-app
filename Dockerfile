# Build stage
#FROM eclipse-temurin:17-jdk-alpine AS build
#WORKDIR /app
#COPY .mvn .mvn
#COPY mvnw pom.xml ./
#RUN ./mvnw dependency:go-offline
#COPY src ./src
#RUN ./mvnw package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-alpine
EXPOSE 8080
ADD target/kubernetes-demo-app-0.0.1-SNAPSHOT.jar kubernetes-demo-app-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "kubernetes-demo-app-0.0.1-SNAPSHOT.jar"]
