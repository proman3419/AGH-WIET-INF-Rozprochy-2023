#!/bin/bash

server_id=$1
echo "Running server with id $server_id"
mvn exec:java -Dexec.mainClass=smarthome.Main -Dexec.args=$server_id
