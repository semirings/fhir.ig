package org.hl7.fhir.services;

import java.io.InputStream;

import org.hl7.fhir.codegen.FieldSpec;
import org.hl7.fhir.codegen.JavaGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class FHIRGeneratorService {

        Logger log = LoggerFactory.getLogger(FHIRGeneratorService.class);

    public void processJson(MultipartFile file) {
        try {
            InputStream jsonStream = file.getInputStream();
            iterateSnapshotsJson(jsonStream);
        } catch (Exception e) {
            log.error(", e");
        }
    }
    

    public void processXml(MultipartFile file) {


    }

    public void iterateSnapshotsJson(InputStream jsonStream) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonStream);

        // Navigate to snapshot.element
        JsonNode snapshot = root.path("snapshot").path("element");
        if (!snapshot.isArray()) {
            throw new IllegalStateException("snapshot.element not found or invalid");
        }

        for (JsonNode element : snapshot) {
            FieldSpec fieldSpec = new FieldSpec():
            fieldSpec.id = element.path("id").asText();
            fieldSpec.path = element.path("path").asText();
            fieldSpec.min = element.path("min").asInt();
            fieldSpec.max = element.path("max").asText();
            String typeNode = element.path("type").asText();

            log.info("Element: " + path + " (id=" + id + ")");
            log.info("Cardinality: " + min + ".." + max);

            if (typeNode.isArray()) {
                for (JsonNode t : typeNode) {
                    String code = t.path("code").asText();
                    System.out.println("Type: " + code);
                }
            }
            JavaGenerator generator = new JavaGenerator();
        /Users/gcr/.ssh/id_ed25519_semirings
    generator.generateClass(typeNode,fieldSpec);
        }
}

    public void iterateSnapshotsXml(InputStream xmlStream) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        JsonNode root = xmlMapper.readTree(xmlStream); // parse XML into JsonNode

        JsonNode snapshot = root.path("snapshot").path("element");
        for (JsonNode element : snapshot) {
            String path = element.path("path").asText();
            String max = element.path("max").asText();
            int min = element.path("min").asInt();

            System.out.println(path + " => " + min + ".." + max);
        }
    }
}
