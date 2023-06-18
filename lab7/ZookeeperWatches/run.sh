#!/bin/bash

mvn compile && mvn exec:java -Dexec.mainClass=zookeeperwatches.Main -Dexec.args="$@"
