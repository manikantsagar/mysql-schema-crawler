package com.vistora.dbcrawler.model.dto;

public class IndexMetadata {
    private String tableName;
    private String indexName;
    private String columnName;
    private boolean isUnique;
    private int ordinalPosition;

    // Constructors
    public IndexMetadata() {}

    public IndexMetadata(String tableName, String indexName, String columnName, boolean isUnique) {
        this.tableName = tableName;
        this.indexName = indexName;
        this.columnName = columnName;
        this.isUnique = isUnique;
    }

    // Getters and Setters
    public String getTableName() { return tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }

    public String getIndexName() { return indexName; }
    public void setIndexName(String indexName) { this.indexName = indexName; }

    public String getColumnName() { return columnName; }
    public void setColumnName(String columnName) { this.columnName = columnName; }

    public boolean isUnique() { return isUnique; }
    public void setUnique(boolean unique) { isUnique = unique; }

    public int getOrdinalPosition() { return ordinalPosition; }
    public void setOrdinalPosition(int ordinalPosition) { this.ordinalPosition = ordinalPosition; }

    @Override
    public String toString() {
        return "IndexMetadata{" +
                "tableName='" + tableName + '\'' +
                ", indexName='" + indexName + '\'' +
                ", columnName='" + columnName + '\'' +
                ", isUnique=" + isUnique +
                '}';
    }
}