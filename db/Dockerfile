FROM postgres:10
ENV POSTGRES_USER moviedb
ENV POSTGRES_PASSWORD p4ssw0rd
ADD schema.sql /docker-entrypoint-initdb.c
EXPOSE 5432