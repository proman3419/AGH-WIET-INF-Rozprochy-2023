#!/bin/bash

JAVA_OUT_PATH="../SmartHomeServer/src/main/java/"
CS_OUT_PATH="../SmartHomeClient/SmartHomeClient/SmartHomeIce"

echo "Generating Java files in ${JAVA_OUT_PATH}"
mkdir -p $JAVA_OUT_PATH
slice2java smarthome.ice --output-dir $JAVA_OUT_PATH
echo "Done"

echo "Generating C# files in ${CS_OUT_PATH}"
mkdir -p $CS_OUT_PATH
slice2cs smarthome.ice --output-dir $CS_OUT_PATH
echo "Done"
