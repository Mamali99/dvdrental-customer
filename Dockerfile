# Verwenden des Eclipse Temurin JRE-Images als Basis
FROM docker.io/library/eclipse-temurin:20-jre

# Setzen Sie das Arbeitsverzeichnis im Container
WORKDIR /usr/customer-app

# Standardwerte für Umgebungsvariablen festlegen
ENV POSTGRES_HOST=localhost
ENV POSTGRES_PORT=54323
ENV POSTGRES_DB=dvdrentalcustomer
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=trust

# Kopieren des Bootable JAR-Files in den Docker-Container
COPY ./target/dvdrental-customer-bootable.jar /usr/customer-app/dvdrental-customer-bootable.jar

# Expose Port 8083 für den Container
EXPOSE 8083

# Setzen der WildFly-Konfiguration, um auf alle Netzwerkschnittstellen zu hören und Port 8083 zu verwenden
CMD java -Djboss.http.port=8083 \
     -Djboss.bind.address=0.0.0.0 \
     -Djboss.bind.address.management=0.0.0.0 \
     -Djboss.management.http.port=9993 \
     -Dpostgresql.host=${POSTGRES_HOST} \
     -Dpostgresql.port=${POSTGRES_PORT} \
     -Dpostgresql.database=${POSTGRES_DB} \
     -Dpostgresql.user=${POSTGRES_USER} \
     -Dpostgresql.password=${POSTGRES_PASSWORD} \
     -jar /usr/customer-app/dvdrental-customer-bootable.jar
