#!/usr/bin/env bash

IMAGE_NAME=jira-pavlichenko
CONTAINER_NAME=jira-pavlichenko-container

echo "#### EXPORTING REQUIRED ENV VARIABLES ####"
cat secure.env
# Не в курсе, сработают ли в маке три строчки ниже, если что сорян )
set -a
source secure.env
set +a


echo "### CLEAN ###"
mvn clean &> /dev/null
docker stop $CONTAINER_NAME &> /dev/null
docker rm  $CONTAINER_NAME &> /dev/null
docker rmi $IMAGE_NAME:latest &> /dev/null


echo "### MAKING JAR ###"
mvn package -Pprod


echo "### MAKING DOCKER IMAGE###"
docker build -t $IMAGE_NAME .


echo "### STARTING CONTAINER ###"
docker run --env-file ./secure.env --network="bridge" -p 8080:8080 --name $CONTAINER_NAME $IMAGE_NAME:latest