FROM postgres:15.1-alpine
MAINTAINER BOBROVICH

ADD /market/ddl/*.sql /docker-entrypoint-initdb.d
ADD /market/dml/*.sql /docker-entrypoint-initdb.d