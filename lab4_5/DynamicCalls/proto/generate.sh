#!/bin/bash

JAVA_PLUGIN_PATH="/d/Documents/agh/semestr_6/rozprochy/tools/protoc-gen-grpc-java"
JAVA_OUT_PATH="../DynamicCallsServer/src/main/java/"

mkdir -p $JAVA_OUT_PATH
echo "Generating Java files in ${JAVA_OUT_PATH}"
protoc -I . --plugin=$JAVA_PLUGIN_PATH --java_out=$JAVA_OUT_PATH --grpc-java_out=$JAVA_OUT_PATH ExecutionService.proto
echo "Done"

# C# files are generated by a plugin
