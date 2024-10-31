#!/usr/bin/env bash

echo "#### EXPORTING REQUIRED ENV VARIABLES ####"

cat secure.env
source secure.env

echo "#### BUILDING APPLICATION ####"

#for prod build
#mvn clean install -Pprod -Dspring.profiles.active=prod -DprofileIdEnabled=true
mvn clean install -Pprod -Dspring.profiles.active=prod -DprofileIdEnabled=true