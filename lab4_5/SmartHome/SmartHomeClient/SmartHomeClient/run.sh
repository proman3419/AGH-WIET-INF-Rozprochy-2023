#!/bin/bash

echo "Building"
dotnet build
echo "Running client"
cd bin/Debug/net6.0/ && ./SmartHomeClient
