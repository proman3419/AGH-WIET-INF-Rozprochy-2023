#!/bin/bash

mvn compile && mvn exec:java  -Dexec.mainClass=chat.main.Main -Dexec.args="c 40000 Alice"