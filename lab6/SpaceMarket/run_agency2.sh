#!/bin/bash

mvn compile && mvn exec:java -Dexec.mainClass=spacemarket.agency.AgencyMain -Dexec.args="Żabka"
