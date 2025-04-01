# AHRQ profile

This is a project that takes an existing, conformant FHIR profile and with it and the FHIR specification, it outputs an ecore that conforms to the profile.

# FHIR Profile to Ecore Converter

This project generates an **Ecore model** from a FHIR **StructureDefinition profile** and the official **FHIR base specification**. The generated Ecore reflects the **snapshot** (complete element tree) and then refines it using the **differential**, making it conformant to the given profile.

There are two profiles included in the src/main/resources directory.  Currently the code reads the file from here.  Other profiles can be added.  Add them here for now.  Each add requires a new build.

---

## 🔍 What It Does

Given:
- A valid FHIR **StructureDefinition** (in XML format),
- A reference to the official **FHIR base definitions** (implicitly loaded if needed),

This tool:
- Loads the `snapshot` from the profile and builds a complete Ecore model.
- Applies the `differential` from the profile to constrain cardinalities and add metadata.
- Outputs an `.ecore` file that conforms to the profile’s structure and constraints.

You can use the resulting Ecore for:
- Code generation (e.g., with Acceleo, Xtend, or EMF)
- DSL development with Xtext
- Validation, modeling, and transformation of FHIR-conformant data

---

## 🛠 Requirements

- Java 17+
- Gradle (or use the provided `gradlew`)
- Internet access (for downloading dependencies)

---

## 📦 Building the Project

Use the provided `bld.sh` script to compile the project:
The output 

```bash
./bld.sh
```
```
./run.sh
```

Where:

Edit the run.sh script to supply the parameters.

-i (or --input) is the path to the StructureDefinition JSON file
-o (or --output) is the destination path for the generated .ecore model. Defaults to build/libs/*.ecore
-h (or --help) is help.

## How It Works Internally

Snapshot processing:
Builds an Ecore structure reflecting the full element tree of the resource.
Differential processing:
Applies constraints from the profile's differential (e.g., min/max cardinality, short descriptions).
Adds annotations for further use in documentation or validation tools.
Serialization:
Uses EMF to save the resulting model to an .ecore file.