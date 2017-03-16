#!/bin/bash

source dockerized_app.cfg

mvn clean package spring-boot:repackage

docker cp ${warfile} hireme_tomcat:/opt/tomcat/webapps/ROOT.war
