package com.vistora.dbcrawler.service;

import com.vistora.dbcrawler.model.dto.TableMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SchemaCrawlerService {

    @Autowired
    private DatabaseMetadataService metadataService;

    @Autowired
    private ModelGenerationService modelGenerationService;

    public Map<String, Object> crawlAndGenerateModels() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // Extract database metadata
            List<TableMetadata> tables = metadataService.extractDatabaseSchema();
            result.put("tables", tables);
            result.put("tableCount", tables.size());
            
            // Generate model classes
            Map<String, String> modelClasses = modelGenerationService.generateModelClasses(tables);
            result.put("modelClasses", modelClasses);
            result.put("generatedClassCount", modelClasses.size());
            
            // Add database info
            Map<String, Object> dbInfo = metadataService.getDatabaseInfo();
            result.put("databaseInfo", dbInfo);
            
            result.put("status", "SUCCESS");
            
        } catch (SQLException e) {
            result.put("status", "ERROR");
            result.put("error", e.getMessage());
        }
        
        return result;
    }
    
    public Map<String, Object> getDatabaseMetadata() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            List<TableMetadata> tables = metadataService.extractDatabaseSchema();
            Map<String, Object> dbInfo = metadataService.getDatabaseInfo();
            
            result.put("tables", tables);
            result.put("databaseInfo", dbInfo);
            result.put("status", "SUCCESS");
            
        } catch (SQLException e) {
            result.put("status", "ERROR");
            result.put("error", e.getMessage());
        }
        
        return result;
    }
}