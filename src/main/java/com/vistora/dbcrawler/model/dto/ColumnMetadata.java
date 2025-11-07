package com.vistora.dbcrawler.model.dto;

public class ColumnMetadata {
    private String tableName;
    private String columnName;
    private String dataType;
    private int columnSize;
    private boolean isNullable;
    private boolean isPrimaryKey;
    private boolean isForeignKey;
    private String foreignKeyTable;
    private String foreignKeyColumn;
    private String defaultValue;

    // Constructors
    public ColumnMetadata() {}

    public ColumnMetadata(String tableName, String columnName, String dataType, 
                         int columnSize, boolean isNullable) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.dataType = dataType;
        this.columnSize = columnSize;
        this.isNullable = isNullable;
    }

    // Getters and Setters
    public String getTableName() { return tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }

    public String getColumnName() { return columnName; }
    public void setColumnName(String columnName) { this.columnName = columnName; }

    public String getDataType() { return dataType; }
    public void setDataType(String dataType) { this.dataType = dataType; }

    public int getColumnSize() { return columnSize; }
    public void setColumnSize(int columnSize) { this.columnSize = columnSize; }

    public boolean isNullable() { return isNullable; }
    public void setNullable(boolean nullable) { isNullable = nullable; }

    public boolean isPrimaryKey() { return isPrimaryKey; }
    public void setPrimaryKey(boolean primaryKey) { isPrimaryKey = primaryKey; }

    public boolean isForeignKey() { return isForeignKey; }
    public void setForeignKey(boolean foreignKey) { isForeignKey = foreignKey; }

    public String getForeignKeyTable() { return foreignKeyTable; }
    public void setForeignKeyTable(String foreignKeyTable) { this.foreignKeyTable = foreignKeyTable; }

    public String getForeignKeyColumn() { return foreignKeyColumn; }
    public void setForeignKeyColumn(String foreignKeyColumn) { this.foreignKeyColumn = foreignKeyColumn; }

    public String getDefaultValue() { return defaultValue; }
    public void setDefaultValue(String defaultValue) { this.defaultValue = defaultValue; }

    @Override
    public String toString() {
        return "ColumnMetadata{" +
                "tableName='" + tableName + '\'' +
                ", columnName='" + columnName + '\'' +
                ", dataType='" + dataType + '\'' +
                ", columnSize=" + columnSize +
                ", isNullable=" + isNullable +
                ", isPrimaryKey=" + isPrimaryKey +
                ", isForeignKey=" + isForeignKey +
                '}';
    }
}