#!/usr/bin/env bash

DIR=$(pwd)
cd build/libs || exit 1
java -jar psoppc.ig-0.0.1.jar -p StructureDefinition-qicore-adverseevent.xml -i fhir.ecore -o out.ecore
cd "$DIR"
