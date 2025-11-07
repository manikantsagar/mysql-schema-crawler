package com.vistora.dbcrawler.service;

import com.vistora.dbcrawler.model.dto.*;
import com.vistora.dbcrawler.model.config.DatabaseConfigProperties;
import com.vistora.dbcrawler.util.TypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ModelGenerationService {

    @Autowired
    private DatabaseConfigProperties config;

    public Map<String, String> generateModelClasses(List<TableMetadata> tables) {
        Map<String, String> modelClasses = new HashMap<>();
        
        for (TableMetadata table : tables) {
            String className = TypeConverter.toPascalCase(table.getTableName());
            String classContent = generateClassContent(table, className);
            modelClasses.put(className, classContent);
        }
        
        return modelClasses;
    }
    
    private String generateClassContent(TableMetadata table, String className) {
        StringBuilder classBuilder = new StringBuilder();
        String packageName = config.getCrawler().getModelPackage();
        boolean useLombok = config.getCrawler().isGenerateLombokAnnotations();
        boolean useJpa = config.getCrawler().isGenerateJpaAnnotations();
        
        // Package declaration
        classBuilder.append("package ").append(packageName).append(";\n\n");
        
        // Imports
        classBuilder.append("import java.util.*;\n");
        if (useJpa) {
            classBuilder.append("import javax.persistence.*;\n");
        }
        if (useLombok) {
            classBuilder.append("import lombok.*;\n");
        }
        classBuilder.append("\n");
        
        // Class annotations
        if (useLombok) {
            classBuilder.append("@Data\n");
            classBuilder.append("@NoArgsConstructor\n");
            classBuilder.append("@AllArgsConstructor\n");
            classBuilder.append("@Builder\n");
        }
        
        if (useJpa) {
            classBuilder.append("@Entity\n");
            classBuilder.append("@Table(name = \"").append(table.getTableName()).append("\")\n");
        }
        
        // Class declaration
        classBuilder.append("public class ").append(className).append(" {\n\n");
        
        // Fields
        for (ColumnMetadata column : table.getColumns()) {
            String fieldName = TypeConverter.toCamelCase(column.getColumnName());
            String fieldType = TypeConverter.sqlTypeToJavaType(column.getDataType(), true);
            
            // Field annotations
            if (useJpa) {
                classBuilder.append("    @Column(name = \"").append(column.getColumnName()).append("\"");
                
                if (column.isNullable()) {
                    classBuilder.append(", nullable = true");
                } else {
                    classBuilder.append(", nullable = false");
                }
                
                if (column.getColumnSize() > 0 && fieldType.equals("String")) {
                    classBuilder.append(", length = ").append(column.getColumnSize());
                }
                
                classBuilder.append(")\n");
                
                if (column.isPrimaryKey()) {
                    classBuilder.append("    @Id\n");
                    if (column.getDataType().equalsIgnoreCase("bigint")) {
                        classBuilder.append("    @GeneratedValue(strategy = GenerationType.IDENTITY)\n");
                    }
                }
            }
            
            // Field declaration
            classBuilder.append("    private ").append(fieldType).append(" ").append(fieldName).append(";\n\n");
        }
        
        // Relationships
        for (Relationship relationship : table.getRelationships()) {
            String relationshipFieldName = TypeConverter.toCamelCase(relationship.getToTable());
            String relationshipClassName = TypeConverter.toPascalCase(relationship.getToTable());
            
            if (useJpa) {
                switch (relationship.getRelationshipType()) {
                    case "ONE_TO_ONE":
                        classBuilder.append("    @OneToOne\n");
                        classBuilder.append("    @JoinColumn(name = \"").append(relationship.getFromColumn()).append("\")\n");
                        break;
                    case "MANY_TO_ONE":
                        classBuilder.append("    @ManyToOne\n");
                        classBuilder.append("    @JoinColumn(name = \"").append(relationship.getFromColumn()).append("\")\n");
                        break;
                    case "ONE_TO_MANY":
                        classBuilder.append("    @OneToMany(mappedBy = \"").append(getMappedByField(table, relationship)).append("\")\n");
                        break;
                    case "MANY_TO_MANY":
                        classBuilder.append("    @ManyToMany\n");
                        classBuilder.append("    @JoinTable(\n");
                        classBuilder.append("        name = \"").append(table.getTableName()).append("\",\n");
                        classBuilder.append("        joinColumns = @JoinColumn(name = \"").append(relationship.getFromColumn()).append("\"),\n");
                        classBuilder.append("        inverseJoinColumns = @JoinColumn(name = \"").append(relationship.getToColumn()).append("\")\n");
                        classBuilder.append("    )\n");
                        break;
                }
            }
            
            String relationshipType = "ONE_TO_MANY".equals(relationship.getRelationshipType()) ? 
                "List<" + relationshipClassName + ">" : relationshipClassName;
            
            classBuilder.append("    private ").append(relationshipType).append(" ").append(relationshipFieldName).append(";\n\n");
        }
        
        // Generate getters and setters if not using Lombok
        if (!useLombok) {
            for (ColumnMetadata column : table.getColumns()) {
                String fieldName = TypeConverter.toCamelCase(column.getColumnName());
                String fieldType = TypeConverter.sqlTypeToJavaType(column.getDataType(), true);
                String pascalFieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                
                // Getter
                classBuilder.append("    public ").append(fieldType).append(" get").append(pascalFieldName).append("() {\n");
                classBuilder.append("        return this.").append(fieldName).append(";\n");
                classBuilder.append("    }\n\n");
                
                // Setter
                classBuilder.append("    public void set").append(pascalFieldName).append("(").append(fieldType).append(" ").append(fieldName).append(") {\n");
                classBuilder.append("        this.").append(fieldName).append(" = ").append(fieldName).append(";\n");
                classBuilder.append("    }\n\n");
            }
        }
        
        classBuilder.append("}");
        return classBuilder.toString();
    }
    
    private String getMappedByField(TableMetadata table, Relationship relationship) {
        // This is a simplified implementation
        // In a real scenario, you'd need more complex logic to determine the mappedBy field
        return TypeConverter.toCamelCase(table.getTableName());
    }
}