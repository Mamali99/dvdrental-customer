#!/bin/bash

# Umgebungsvariablen definieren
export POSTGRES_HOST=localhost
export POSTGRES_PORT=54323
export POSTGRES_DB=dvdrentalcustomer
export POSTGRES_USER=postgres
export POSTGRES_PASSWORD=trust

# Setze den Port für die Anwendung
export HTTP_PORT=8083

# Führe den bootfähigen JAR aus mit den Systemeigenschaften
java -Djboss.http.port=${HTTP_PORT} \
     -Djboss.bind.address=0.0.0.0 \
     -Djboss.bind.address.management=0.0.0.0 \
     -Djboss.management.http.port=9993 \
          -Denv.POSTGRES_HOST=${POSTGRES_HOST} \
          -Denv.POSTGRES_PORT=${POSTGRES_PORT} \
          -Denv.POSTGRES_DB=${POSTGRES_DB} \
          -Denv.POSTGRES_USER=${POSTGRES_USER} \
          -Denv.POSTGRES_PASSWORD=${POSTGRES_PASSWORD} \
     -jar target/dvdrental-customer-bootable.jar
