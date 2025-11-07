package com.vistora.dbcrawler.model.dto;

import java.util.ArrayList;
import java.util.List;

public class TableMetadata {
    private String tableName;
    private String tableType;
    private String remarks;
    private List<ColumnMetadata> columns = new ArrayList<>();
    private List<IndexMetadata> indexes = new ArrayList<>();
    private List<Relationship> relationships = new ArrayList<>();

    // Constructors
    public TableMetadata() {}

    public TableMetadata(String tableName, String tableType) {
        this.tableName = tableName;
        this.tableType = tableType;
    }

    // Getters and Setters
    public String getTableName() { return tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }

    public String getTableType() { return tableType; }
    public void setTableType(String tableType) { this.tableType = tableType; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public List<ColumnMetadata> getColumns() { return columns; }
    public void setColumns(List<ColumnMetadata> columns) { this.columns = columns; }

    public List<IndexMetadata> getIndexes() { return indexes; }
    public void setIndexes(List<IndexMetadata> indexes) { this.indexes = indexes; }

    public List<Relationship> getRelationships() { return relationships; }
    public void setRelationships(List<Relationship> relationships) { this.relationships = relationships; }

    public void addColumn(ColumnMetadata column) {
        this.columns.add(column);
    }

    public void addIndex(IndexMetadata index) {
        this.indexes.add(index);
    }

    public void addRelationship(Relationship relationship) {
        this.relationships.add(relationship);
    }

    @Override
    public String toString() {
        return "TableMetadata{" +
                "tableName='" + tableName + '\'' +
                ", tableType='" + tableType + '\'' +
                ", columns=" + columns.size() +
                ", indexes=" + indexes.size() +
                ", relationships=" + relationships.size() +
                '}';
    }
}