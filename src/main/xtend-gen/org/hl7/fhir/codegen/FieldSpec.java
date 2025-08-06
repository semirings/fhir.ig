package org.hl7.fhir.codegen;

/**
 * FieldSpec captures the FHIR element information needed to generate Java code
 */
@SuppressWarnings("all")
public class FieldSpec {
  public String path;

  public String type;

  public int min;

  public String max;
}
