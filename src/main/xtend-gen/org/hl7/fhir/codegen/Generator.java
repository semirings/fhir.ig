package org.hl7.fhir.codegen;

import java.util.List;

@SuppressWarnings("all")
public interface Generator {
  String generateClass(final String className, final List<FieldSpec> fields);
}
