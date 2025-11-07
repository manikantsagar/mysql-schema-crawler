package com.vistora.dbcrawler.controller;

import com.vistora.dbcrawler.service.SchemaCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/models")
@CrossOrigin(origins = "*")
public class ModelController {

    @Autowired
    private SchemaCrawlerService schemaCrawlerService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllModels() {
        Map<String, Object> result = schemaCrawlerService.crawlAndGenerateModels();
        
        @SuppressWarnings("unchecked")
        Map<String, String> modelClasses = (Map<String, String>) result.get("modelClasses");
        
        Map<String, Object> response = Map.of(
            "models", modelClasses,
            "count", modelClasses != null ? modelClasses.size() : 0
        );
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/download")
    public ResponseEntity<String> downloadModels() {
        Map<String, Object> result = schemaCrawlerService.crawlAndGenerateModels();
        
        @SuppressWarnings("unchecked")
        Map<String, String> modelClasses = (Map<String, String>) result.get("modelClasses");
        
        StringBuilder zipContent = new StringBuilder();
        zipContent.append("=== GENERATED MODEL CLASSES ===\n\n");
        
        if (modelClasses != null) {
            for (Map.Entry<String, String> entry : modelClasses.entrySet()) {
                zipContent.append("// File: ").append(entry.getKey()).append(".java\n");
                zipContent.append(entry.getValue());
                zipContent.append("\n\n// ============================\n\n");
            }
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=generated-models.txt");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(zipContent.toString());
    }
}