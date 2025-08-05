package org.hl7.fhir;

import java.io.IOException;
import java.lang.Boolean;
import java.lang.String;

import org.hl7.fhir.services.FHIRGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FHIRGeneratorController {

    Logger log = LoggerFactory.getLogger(FHIRGeneratorController.class);
    
    FHIRGeneratorService service;

    @Autowired
    public FHIRGeneratorController(FHIRGeneratorService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index() {
        log.trace("Start==>");
        return "index.html";
    }

    @PostMapping(
        path = "/profile/{version}/{language}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Boolean uploadProfile(
            @PathVariable String version,
            @PathVariable String language,
            @RequestBody String profile) {

        // Process profile here
        return Boolean.TRUE;
    }
    
    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        String contentType = file.getContentType();
        if ("application/json".equalsIgnoreCase(contentType)) {
            service.process(file);
            return ResponseEntity.ok("JSON file uploaded");
        } else if ("application/xml".equalsIgnoreCase(contentType) || "text/xml".equalsIgnoreCase(contentType)) {
            return ResponseEntity.ok("XML file uploaded");
        } else {
            return ResponseEntity.badRequest().body("Unsupported file type: " + contentType);
        }
    }
}