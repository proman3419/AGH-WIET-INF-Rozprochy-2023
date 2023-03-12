#!/bin/bash

mvn compile && mvn exec:java  -Dexec.mainClass=chat.main.Main -Dexec.args="c 50000 Bob"