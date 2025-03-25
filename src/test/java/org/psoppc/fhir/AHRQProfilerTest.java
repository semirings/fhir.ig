package org.psoppc.fhir;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.hl7.fhir.ElementDefinition;
import org.hl7.fhir.StructureDefinition;
import org.hl7.fhir.StructureDefinitionSnapshot;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AHRQProfilerTest {

	static AHRQProfiler app;
	static EObject profile;
	static EObject spec;

	@BeforeAll
	static void beforAll() {
		app = new AHRQProfiler();
		// InputStream readerProfile = AHRQProfilerTest.class.getClassLoader().getResourceAsStream("StructureDefinition-de-identified-uds-plus-patient.xml");
		// assertNotNull(readerProfile);
		// profile = FHIRSerDeser.load(readerProfile, Finals.SDS_FORMAT.XML);
		// assertNotNull(profile);
		// assertTrue(profile instanceof StructureDefinition);
		// InputStream readerSpec = AHRQProfilerTest.class.getClassLoader().getResourceAsStream("fhir.ecore");
		// assertNotNull(readerSpec);
		// spec = FHIRSerDeser.load(readerSpec, Finals.SDS_FORMAT.ECORE);
		// assertNotNull(spec);
	}

	//@Test
	void testRun() {
		System.out.println(profile.eClass().getName());
//		System.out.println(spec.eClass().getName());
		assertTrue(true);
	}

	@Test 
	void testLoadProfile() {
		StructureDefinition profile = app.loadProfile();
		assertNotNull(profile);
	}

	@Test 
	void testLoadSpec() {
		EPackage spec = app.loadSpec();
		assertNotNull(spec);
	}
	@Test
	void testPopulateEcoreOut() {
		StructureDefinition profile = app.loadProfile();
		EPackage spec = app.loadSpec();
		EPackage out = app.copySpec(spec);
		app.clearClassifiers(out);
		assertEquals(0, out.getEClassifiers().size());

		StructureDefinitionSnapshot snap = profile.getSnapshot();
		app.populateEcoreOut(snap, spec, out);

	}

	@Test
	void testApplySnapshotElementToFeature() {
		StructureDefinition profile = app.loadProfile();
		EPackage spec = app.loadSpec();
		EPackage out = app.copySpec(spec);
		app.clearClassifiers(out);
		assertEquals(0, out.getEClassifiers().size());

		StructureDefinitionSnapshot snap = profile.getSnapshot();
		EStructuralFeature feature = snap.eClass().getEStructuralFeature("actuality");
		Object obj = snap.eGet(feature);
		ElementDefinition elem = null;
		if (obj instanceof ElementDefinition) {
			elem = (ElementDefinition)obj;
		}

		app.applySnapshotElementToFeature(elem, feature);
		assertNotNull(feature);
	}
}
