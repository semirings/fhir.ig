#!/usr/bin/env bash

DIR=$(pwd)
cd build/libs || exit 1
java -jar org.psoppc.fhir.AHRQProfiler.jar
cd "$DIR"
