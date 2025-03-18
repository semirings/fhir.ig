package org.hl7.fhir;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Ecore {

	private static final Logger log = LoggerFactory.getLogger(Ecore.class);

	public static File getFhirEcore() {
		return new File("model/fhir.ecore");
	}

	public static void main(String[] args) {
			log.info("This package FHIR EMF is not executable.  Its use is as a dependency."
		);
	}
}
