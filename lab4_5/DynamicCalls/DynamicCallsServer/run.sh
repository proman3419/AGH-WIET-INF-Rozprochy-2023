#!/bin/bash

echo "Building"
mvn install
echo "Running server"
mvn exec:java -Dexec.mainClass=dynamiccalls.Main
