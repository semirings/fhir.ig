package org.hl7.fhir.codegen

import java.util.List

interface Generator {
    def String generateClass(String className, List<FieldSpec> fields)
}