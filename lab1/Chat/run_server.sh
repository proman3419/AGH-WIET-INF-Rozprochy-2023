#!/bin/bash

mvn compile && mvn exec:java -Dexec.mainClass=chat.main.Main -Dexec.args="s 50000 d"
