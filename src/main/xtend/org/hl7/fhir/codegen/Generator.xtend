package org.hl7.fhir.codegen

public interface Generator {
    def String generateClass(String className, List<FieldSpec> fields)
}