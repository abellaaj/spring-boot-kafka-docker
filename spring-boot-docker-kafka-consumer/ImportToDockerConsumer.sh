#!/bin/bash

mvn clean install
docker build -t "spring-boot-docker-kafka-consumer:0.0.1" .
docker tag spring-boot-docker-kafka-consumer:0.0.1 picnex000.dom101.mapres:8084/bpcesa/siq/spring-boot-docker-kafka-consumer:0.0.1
docker push picnex000.dom101.mapres:8084/bpcesa/siq/spring-boot-docker-kafka-consumer:0.0.1
oc import-image spring-boot-docker-kafka-consumer:0.0.1 --from=picnex000.dom101.mapres:8084/bpcesa/siq/spring-boot-docker-kafka-consumer:0.0.1 --confirm=true  --insecure=true
