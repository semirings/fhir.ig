package org.psoppc.fhir;

import java.io.InputStream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.hl7.fhir.FhirPackage;
import org.hl7.fhir.emf.FHIRSerDeser;
import org.hl7.fhir.emf.Finals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3._1999.xhtml.XhtmlPackage;
import org.w3.xml._1998.namespace.NamespacePackage;

public class AHRQProfiler implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(AHRQProfiler.class);

	private static ResourceSet resourceSet = new ResourceSetImpl();
	private static Resource resource;
	
	static {
		resourceSet.getPackageRegistry().put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(FhirPackage.eNS_URI, FhirPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(XhtmlPackage.eNS_URI, XhtmlPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(NamespacePackage.eNS_URI, NamespacePackage.eINSTANCE);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
	}

	public void run() {
		InputStream reader = AHRQProfiler.class.getClassLoader().getResourceAsStream("StructureDefinition-de-identified-uds-plus-patient.xml");
		InputStream reader1 = AHRQProfiler.class.getClassLoader().getResourceAsStream("fhir.ecore");
		EObject eObject = FHIRSerDeser.load(reader, Finals.SDS_FORMAT.XML);

	}

	public static void main(String[] args) {
		AHRQProfiler app = new AHRQProfiler();
		app.run();
	}

	// public static OutputStream profileResource(EObject eObject) {
	// 	URI ecoreURI =  URI.createFileURI("data/fhir.ecore");
	// 	resource = resourceSet.getResource(ecoreURI, true);
	// 	ByteArrayOutputStream writer = null;
	// 	try {
	// 		writer = new ByteArrayOutputStream();
	// 		resource.save(System.out, Collections.EMPTY_MAP);
	// 		writer.close();
	// 	} catch (JsonProcessingException e) {
	// 		log.error("", e);
	// 	} catch (IOException e) {
	// 		log.error("", e);
	// 	}
	// 	return writer;

	// }

	// void loadParse() {
		
	// 	ResourceSet resourceSet = new ResourceSetImpl();
	// 	Resource fhirResource = resourceSet.getResource(URI.createFileURI("fhir.ecore"), true);
	// 	Resource profileResource = resourceSet.getResource(URI.createFileURI("custom_profile.ecore"), true);
		
	// 	EObject fhirRoot = fhirResource.getContents().get(0);
	// 	EObject profileRoot = profileResource.getContents().get(0);
	// }

	// void iterateElements() {
	// 	for (EClassifier classifier : fhirEPackage.getEClassifiers()) {
	// 		if (classifier instanceof EClass) {
	// 			EClass eClass = (EClass) classifier;
	// 			if (profileDefinesRestrictions(eClass)) {
	// 				applyProfileConstraints(eClass, profileEPackage);
	// 			}
	// 		}
	// 	}		
	// }

	// void merge() {
	// 	Resource newEcoreResource = resourceSet.createResource(URI.createFileURI("profiled_fhir.ecore"));
	// 	newEcoreResource.getContents().add(fhirRoot);
	// 	newEcoreResource.save(Collections.EMPTY_MAP);
	// }
}
