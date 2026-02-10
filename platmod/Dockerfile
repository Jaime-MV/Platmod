# ETAPA 1: CONSTRUCCIÓN (Usamos Maven para crear el .jar)
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# ETAPA 2: EJECUCIÓN (Usamos un Java ligero para correr la API)
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]