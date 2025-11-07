package com.vistora.dbcrawler.service;

import com.vistora.dbcrawler.model.dto.*;
import com.vistora.dbcrawler.model.config.DatabaseConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;

@Service
public class DatabaseMetadataService {

    @Autowired
    private Connection connection;

    @Autowired
    private DatabaseConfigProperties config;

    public List<TableMetadata> extractDatabaseSchema() throws SQLException {
        List<TableMetadata> tables = new ArrayList<>();
        DatabaseMetaData metaData = connection.getMetaData();
        
        String schema = config.getCrawler().getSchemaName();
        
        // Get all tables
        try (ResultSet tablesResult = metaData.getTables(null, schema, "%", new String[]{"TABLE"})) {
            while (tablesResult.next()) {
                String tableName = tablesResult.getString("TABLE_NAME");
                String tableType = tablesResult.getString("TABLE_TYPE");
                String remarks = tablesResult.getString("REMARKS");
                
                TableMetadata table = new TableMetadata(tableName, tableType);
                table.setRemarks(remarks);
                
                // Extract columns
                extractColumns(metaData, schema, table);
                
                // Extract primary keys
                extractPrimaryKeys(metaData, schema, table);
                
                // Extract foreign keys
                extractForeignKeys(metaData, schema, table);
                
                // Extract indexes
                extractIndexes(metaData, schema, table);
                
                tables.add(table);
            }
        }
        
        // Analyze relationships
        analyzeRelationships(tables);
        
        return tables;
    }
    
    private void extractColumns(DatabaseMetaData metaData, String schema, TableMetadata table) throws SQLException {
        try (ResultSet columnsResult = metaData.getColumns(null, schema, table.getTableName(), "%")) {
            while (columnsResult.next()) {
                String columnName = columnsResult.getString("COLUMN_NAME");
                String dataType = columnsResult.getString("TYPE_NAME");
                int columnSize = columnsResult.getInt("COLUMN_SIZE");
                boolean isNullable = "YES".equals(columnsResult.getString("IS_NULLABLE"));
                String defaultValue = columnsResult.getString("COLUMN_DEF");
                
                ColumnMetadata column = new ColumnMetadata(table.getTableName(), columnName, dataType, columnSize, isNullable);
                column.setDefaultValue(defaultValue);
                table.addColumn(column);
            }
        }
    }
    
    private void extractPrimaryKeys(DatabaseMetaData metaData, String schema, TableMetadata table) throws SQLException {
        try (ResultSet pkResult = metaData.getPrimaryKeys(null, schema, table.getTableName())) {
            Set<String> primaryKeys = new HashSet<>();
            while (pkResult.next()) {
                String pkColumn = pkResult.getString("COLUMN_NAME");
                primaryKeys.add(pkColumn);
            }
            
            // Mark primary key columns
            for (ColumnMetadata column : table.getColumns()) {
                if (primaryKeys.contains(column.getColumnName())) {
                    column.setPrimaryKey(true);
                }
            }
        }
    }
    
    private void extractForeignKeys(DatabaseMetaData metaData, String schema, TableMetadata table) throws SQLException {
        try (ResultSet fkResult = metaData.getImportedKeys(null, schema, table.getTableName())) {
            while (fkResult.next()) {
                String fkColumnName = fkResult.getString("FKCOLUMN_NAME");
                String pkTableName = fkResult.getString("PKTABLE_NAME");
                String pkColumnName = fkResult.getString("PKCOLUMN_NAME");
                
                // Mark foreign key columns
                for (ColumnMetadata column : table.getColumns()) {
                    if (column.getColumnName().equals(fkColumnName)) {
                        column.setForeignKey(true);
                        column.setForeignKeyTable(pkTableName);
                        column.setForeignKeyColumn(pkColumnName);
                        
                        // Create relationship
                        Relationship relationship = new Relationship(
                            table.getTableName(), fkColumnName, pkTableName, pkColumnName
                        );
                        table.addRelationship(relationship);
                    }
                }
            }
        }
    }
    
    private void extractIndexes(DatabaseMetaData metaData, String schema, TableMetadata table) throws SQLException {
        try (ResultSet indexResult = metaData.getIndexInfo(null, schema, table.getTableName(), false, true)) {
            while (indexResult.next()) {
                String indexName = indexResult.getString("INDEX_NAME");
                String columnName = indexResult.getString("COLUMN_NAME");
                boolean isUnique = !indexResult.getBoolean("NON_UNIQUE");
                int ordinalPosition = indexResult.getInt("ORDINAL_POSITION");
                
                if (indexName != null && !indexName.startsWith("PRIMARY")) {
                    IndexMetadata index = new IndexMetadata(table.getTableName(), indexName, columnName, isUnique);
                    index.setOrdinalPosition(ordinalPosition);
                    table.addIndex(index);
                }
            }
        }
    }
    
    private void analyzeRelationships(List<TableMetadata> tables) {
        Map<String, TableMetadata> tableMap = new HashMap<>();
        for (TableMetadata table : tables) {
            tableMap.put(table.getTableName().toLowerCase(), table);
        }
        
        for (TableMetadata table : tables) {
            for (Relationship relationship : table.getRelationships()) {
                String toTable = relationship.getToTable();
                TableMetadata targetTable = tableMap.get(toTable.toLowerCase());
                
                if (targetTable != null) {
                    // Check if this is a many-to-many relationship (join table)
                    if (isJoinTable(table)) {
                        relationship.setRelationshipType("MANY_TO_MANY");
                    } else {
                        // Check if the target table has a foreign key back to this table
                        boolean hasReverseRelationship = hasReverseRelationship(targetTable, table.getTableName());
                        if (hasReverseRelationship) {
                            relationship.setRelationshipType("ONE_TO_ONE");
                        } else {
                            relationship.setRelationshipType("MANY_TO_ONE");
                        }
                    }
                }
            }
        }
    }
    
    private boolean isJoinTable(TableMetadata table) {
        // A join table typically has exactly two foreign keys and few other columns
        long foreignKeyCount = table.getColumns().stream()
                .filter(ColumnMetadata::isForeignKey)
                .count();
        
        return foreignKeyCount == 2 && table.getColumns().size() <= 3;
    }
    
    private boolean hasReverseRelationship(TableMetadata table, String targetTableName) {
        return table.getRelationships().stream()
                .anyMatch(rel -> rel.getToTable().equalsIgnoreCase(targetTableName));
    }
    
    public Map<String, Object> getDatabaseInfo() throws SQLException {
        Map<String, Object> dbInfo = new HashMap<>();
        DatabaseMetaData metaData = connection.getMetaData();
        
        dbInfo.put("databaseProductName", metaData.getDatabaseProductName());
        dbInfo.put("databaseProductVersion", metaData.getDatabaseProductVersion());
        dbInfo.put("driverName", metaData.getDriverName());
        dbInfo.put("driverVersion", metaData.getDriverVersion());
        dbInfo.put("url", metaData.getURL());
        dbInfo.put("userName", metaData.getUserName());
        
        return dbInfo;
    }
}