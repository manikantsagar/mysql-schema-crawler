package com.vistora.dbcrawler.controller;

import com.vistora.dbcrawler.service.SchemaCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/crawler")
@CrossOrigin(origins = "*")
public class CrawlerController {

    @Autowired
    private SchemaCrawlerService schemaCrawlerService;

    @GetMapping("/metadata")
    public ResponseEntity<Map<String, Object>> getDatabaseMetadata() {
        Map<String, Object> metadata = schemaCrawlerService.getDatabaseMetadata();
        return ResponseEntity.ok(metadata);
    }

    @PostMapping("/generate-models")
    public ResponseEntity<Map<String, Object>> generateModels() {
        Map<String, Object> result = schemaCrawlerService.crawlAndGenerateModels();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "Database Schema Crawler"));
    }
}