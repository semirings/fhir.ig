#!/usr/bin/env bash

DIR=$(pwd)
cd build/libs || exit 1
# java -jar ahrq.profile-0.0.1.jar
java -cp "./:libs/*" org.psoppc.fhir.AHRQProfiler
cd "$DIR"
