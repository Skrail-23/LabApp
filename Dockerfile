# Dockerfile
# Verwende OpenJDK 17 als Basis-Image
FROM openjdk:17-jdk-slim

# Setze das Arbeitsverzeichnis im Container
WORKDIR /app

# Kopiere das erstellte Jar in das Image
COPY target/cloud-usage-time-calculator-0.0.1-SNAPSHOT.jar app.jar

# Der Befehl, um die Anwendung zu starten
ENTRYPOINT ["java", "-jar", "app.jar"]
