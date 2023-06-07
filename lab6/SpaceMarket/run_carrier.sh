#!/bin/bash

mvn compile && mvn exec:java -Dexec.mainClass=spacemarket.carrier.CarrierMain -Dexec.args="$@"
