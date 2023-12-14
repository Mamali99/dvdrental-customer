
FROM docker.io/library/eclipse-temurin:20-jre


WORKDIR /usr/customer-app


ENV POSTGRESQL_USER=postgres
ENV POSTGRESQL_PASSWORD=trust

COPY ./target/dvdrental-customer-bootable.jar /usr/dvdrental-customer/dvdrental-customer-bootable.jar


EXPOSE 8083

CMD java -Djboss.bind.address=0.0.0.0 -Djboss.bind.address.management=0.0.0.0 -Djboss.http.port=8083 -Djboss.management.http.port=9993 -jar /usr/dvdrental-customer/dvdrental-customer-bootable.jar -Dpostgresql.user=${POSTGRESQL_USER} -Dpostgresql.password=${POSTGRESQL_PASSWORD}