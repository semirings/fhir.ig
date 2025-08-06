package org.hl7.fhir.codegen;

import java.util.List;
import java.util.Objects;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

/**
 * JavaGenerator produces Java classes with fields + getters/setters.
 * - Handles lists for cardinality > 1 or "*"
 * - Follows JavaBean naming conventions
 */
@SuppressWarnings("all")
public class JavaGenerator implements Generator {
  /**
   * Generate full class source
   */
  @Override
  public String generateClass(final String className, final List<FieldSpec> fields) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public class ");
    _builder.append(className);
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      for(final FieldSpec field : fields) {
        String _generateFieldDeclaration = this.generateFieldDeclaration(field);
        _builder.append(_generateFieldDeclaration);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      for(final FieldSpec field_1 : fields) {
        String _generateGetter = this.generateGetter(field_1);
        _builder.append(_generateGetter);
        _builder.newLineIfNotEmpty();
        {
          boolean _isList = this.isList(field_1);
          boolean _not = (!_isList);
          if (_not) {
            String _generateSetter = this.generateSetter(field_1);
            _builder.append(_generateSetter);
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }

  /**
   * Generate field declaration based on cardinality
   */
  private String generateFieldDeclaration(final FieldSpec field) {
    String _xifexpression = null;
    boolean _isList = this.isList(field);
    if (_isList) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("// Cardinality: ");
      _builder.append(field.min);
      _builder.append("..");
      _builder.append(field.max);
      _builder.newLineIfNotEmpty();
      _builder.append("private java.util.List<");
      String _mapType = this.mapType(field.type);
      _builder.append(_mapType);
      _builder.append("> ");
      String _javaName = this.toJavaName(field.path);
      _builder.append(_javaName);
      _builder.append(" = new java.util.ArrayList<>();");
      _builder.newLineIfNotEmpty();
      _xifexpression = _builder.toString();
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("// Cardinality: ");
      _builder_1.append(field.min);
      _builder_1.append("..");
      _builder_1.append(field.max);
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("private ");
      String _mapType_1 = this.mapType(field.type);
      _builder_1.append(_mapType_1);
      _builder_1.append(" ");
      String _javaName_1 = this.toJavaName(field.path);
      _builder_1.append(_javaName_1);
      _builder_1.append(";");
      _builder_1.newLineIfNotEmpty();
      _xifexpression = _builder_1.toString();
    }
    return _xifexpression;
  }

  /**
   * Generate getter (list getters have no setter)
   */
  private String generateGetter(final FieldSpec field) {
    String _xblockexpression = null;
    {
      final String javaName = this.toJavaName(field.path);
      String _xifexpression = null;
      boolean _isList = this.isList(field);
      if (_isList) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("public java.util.List<");
        String _mapType = this.mapType(field.type);
        _builder.append(_mapType);
        _builder.append("> get");
        String _firstUpper = StringExtensions.toFirstUpper(javaName);
        _builder.append(_firstUpper);
        _builder.append("() {");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("return ");
        _builder.append(javaName, "    ");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("}");
        _builder.newLine();
        _xifexpression = _builder.toString();
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("public ");
        String _mapType_1 = this.mapType(field.type);
        _builder_1.append(_mapType_1);
        _builder_1.append(" get");
        String _firstUpper_1 = StringExtensions.toFirstUpper(javaName);
        _builder_1.append(_firstUpper_1);
        _builder_1.append("() {");
        _builder_1.newLineIfNotEmpty();
        _builder_1.append("    ");
        _builder_1.append("return ");
        _builder_1.append(javaName, "    ");
        _builder_1.append(";");
        _builder_1.newLineIfNotEmpty();
        _builder_1.append("}");
        _builder_1.newLine();
        _xifexpression = _builder_1.toString();
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }

  /**
   * Generate setter for single-valued fields
   */
  private String generateSetter(final FieldSpec field) {
    String _xblockexpression = null;
    {
      final String javaName = this.toJavaName(field.path);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("public void set");
      String _firstUpper = StringExtensions.toFirstUpper(javaName);
      _builder.append(_firstUpper);
      _builder.append("(");
      String _mapType = this.mapType(field.type);
      _builder.append(_mapType);
      _builder.append(" ");
      _builder.append(javaName);
      _builder.append(") {");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.append("this.");
      _builder.append(javaName, "    ");
      _builder.append(" = ");
      _builder.append(javaName, "    ");
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      _builder.append("}");
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }

  /**
   * Map FHIR type to Java type
   */
  private String mapType(final String fhirType) {
    String _switchResult = null;
    if (fhirType != null) {
      switch (fhirType) {
        case "string":
          _switchResult = "String";
          break;
        case "boolean":
          _switchResult = "boolean";
          break;
        case "integer":
          _switchResult = "int";
          break;
        default:
          _switchResult = "String";
          break;
      }
    } else {
      _switchResult = "String";
    }
    return _switchResult;
  }

  /**
   * Determine if the field is multi-valued
   */
  private boolean isList(final FieldSpec field) {
    boolean _or = false;
    boolean _equals = Objects.equals(field.max, "*");
    if (_equals) {
      _or = true;
    } else {
      boolean _xtrycatchfinallyexpression = false;
      try {
        int _parseInt = Integer.parseInt(field.max);
        _xtrycatchfinallyexpression = (_parseInt > 1);
      } catch (final Throwable _t) {
        if (_t instanceof Exception) {
          _xtrycatchfinallyexpression = false;
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
      _or = _xtrycatchfinallyexpression;
    }
    return _or;
  }

  /**
   * Extract property name from FHIR path
   */
  private String toJavaName(final String path) {
    String _xblockexpression = null;
    {
      final int lastDot = path.lastIndexOf(".");
      String _xifexpression = null;
      if ((lastDot == (-1))) {
        _xifexpression = path;
      } else {
        _xifexpression = path.substring((lastDot + 1));
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
}
