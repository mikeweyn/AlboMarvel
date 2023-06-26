#!/bin/bash
docker run --rm -p 80:8070 \
--name=alboMarvel \
-v "$PWD":/usr/src/myapp \
-w /usr/src/myapp \
openjdk:11-jre java -jar target/albomarvel.jar