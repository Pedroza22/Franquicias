# =========================================================
# FASE 1: BUILD (Compilación)
# Usamos una imagen de Maven para compilar el código.
# =========================================================
FROM maven:3.9.5-eclipse-temurin-17 AS builder

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos de configuración (pom.xml) y descárga las dependencias
# (Mejora la velocidad de caché de Docker)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia el código fuente completo
COPY src ./src

# Compila y empaqueta el proyecto, produciendo el JAR en target/
RUN mvn clean install -DskipTests

# =========================================================
# FASE 2: RUNTIME (Ejecución)
# Usamos una imagen base más ligera (solo Java Runtime Environment)
# =========================================================
FROM eclipse-temurin:17-jre-alpine

# Define un volumen para los logs (buena práctica de observabilidad, Senode)
VOLUME /tmp

# Copia el JAR generado en la fase 'builder' al directorio de ejecución
ARG JAR_FILE=/app/target/franquicias-0.0.1-SNAPSHOT.jar
COPY --from=builder ${JAR_FILE} app.jar

# Expone el puerto por defecto de Spring Boot (8080)
EXPOSE 8080

# Comando para ejecutar la aplicación (el punto de entrada)
ENTRYPOINT ["java","-jar","/app.jar"]