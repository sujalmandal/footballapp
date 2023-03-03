#!/bin/sh

echo "*********************************"
echo "starting Football app"
echo "*********************************"

java $JAVA_OPT -Dspring-profiles-active=$PROFILE -jar /usr/local/football-app/football-app-0.0.1-SNAPSHOT.jar
