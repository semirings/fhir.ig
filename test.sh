#!/usr/bin/env bash

clear; 
./gradlew clean :test --tests org.psoppc.fhir.AHRQProfilerTest
