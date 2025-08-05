package org.hl7.fhir.codegen

import java.util.List

/**
 * FieldSpec captures the FHIR element information needed to generate Java code
 */
class FieldSpec {
    public String path        // Full FHIR path, e.g. "Patient.name.family"
    public String type        // FHIR type, e.g. "string", "boolean", "integer"
    public int min            // min cardinality
    public String max         // max cardinality ("*" or numeric)
}

/**
 * JavaGenerator produces Java classes with fields + getters/setters.
 * - Handles lists for cardinality > 1 or "*"
 * - Follows JavaBean naming conventions
 */
class JavaGenerator implements Generator {

    /**
     * Generate full class source
     */
    override String generateClass(String className, List<FieldSpec> fields) '''
        public class «className» {

        «FOR field : fields»
            «generateFieldDeclaration(field)»
        «ENDFOR»

        «FOR field : fields»
            «generateGetter(field)»
            «IF !isList(field)»
                «generateSetter(field)»
            «ENDIF»
        «ENDFOR»
        }
    '''

    /**
     * Generate field declaration based on cardinality
     */
    def private String generateFieldDeclaration(FieldSpec field) {
        if (isList(field)) {
            '''
            // Cardinality: «field.min»..«field.max»
            private java.util.List<«mapType(field.type)»> «toJavaName(field.path)» = new java.util.ArrayList<>();
            '''
        } else {
            '''
            // Cardinality: «field.min»..«field.max»
            private «mapType(field.type)» «toJavaName(field.path)»;
            '''
        }
    }

    /**
     * Generate getter (list getters have no setter)
     */
    def private String generateGetter(FieldSpec field) {
        val javaName = toJavaName(field.path)
        if (isList(field)) {
            '''
            public java.util.List<«mapType(field.type)»> get«javaName.toFirstUpper()»() {
                return «javaName»;
            }
            '''
        } else {
            '''
            public «mapType(field.type)» get«javaName.toFirstUpper()»() {
                return «javaName»;
            }
            '''
        }
    }

    /**
     * Generate setter for single-valued fields
     */
    def private String generateSetter(FieldSpec field) {
        val javaName = toJavaName(field.path)
        '''
        public void set«javaName.toFirstUpper()»(«mapType(field.type)» «javaName») {
            this.«javaName» = «javaName»;
        }
        '''
    }

    /**
     * Map FHIR type to Java type
     */
    def private String mapType(String fhirType) {
        switch fhirType {
            case "string": "String"
            case "boolean": "boolean"
            case "integer": "int"
            default: "String" // fallback for complex/unknown types
        }
    }

    /**
     * Determine if the field is multi-valued
     */
    def private boolean isList(FieldSpec field) {
        field.max == "*" || (try { Integer.parseInt(field.max) > 1 } catch(Exception e) { false })
    }

    /**
     * Extract property name from FHIR path
     */
    def private String toJavaName(String path) {
        val lastDot = path.lastIndexOf('.')
        if (lastDot == -1) path else path.substring(lastDot + 1)
    }
}
