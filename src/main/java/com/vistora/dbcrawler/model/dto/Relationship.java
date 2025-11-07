package com.vistora.dbcrawler.model.dto;

public class Relationship {
    private String relationshipName;
    private String fromTable;
    private String fromColumn;
    private String toTable;
    private String toColumn;
    private String relationshipType; // ONE_TO_ONE, ONE_TO_MANY, MANY_TO_ONE, MANY_TO_MANY

    // Constructors
    public Relationship() {}

    public Relationship(String fromTable, String fromColumn, String toTable, String toColumn) {
        this.fromTable = fromTable;
        this.fromColumn = fromColumn;
        this.toTable = toTable;
        this.toColumn = toColumn;
        this.relationshipName = fromTable + "_" + toTable + "_FK";
    }

    // Getters and Setters
    public String getRelationshipName() { return relationshipName; }
    public void setRelationshipName(String relationshipName) { this.relationshipName = relationshipName; }

    public String getFromTable() { return fromTable; }
    public void setFromTable(String fromTable) { this.fromTable = fromTable; }

    public String getFromColumn() { return fromColumn; }
    public void setFromColumn(String fromColumn) { this.fromColumn = fromColumn; }

    public String getToTable() { return toTable; }
    public void setToTable(String toTable) { this.toTable = toTable; }

    public String getToColumn() { return toColumn; }
    public void setToColumn(String toColumn) { this.toColumn = toColumn; }

    public String getRelationshipType() { return relationshipType; }
    public void setRelationshipType(String relationshipType) { this.relationshipType = relationshipType; }

    @Override
    public String toString() {
        return "Relationship{" +
                "relationshipName='" + relationshipName + '\'' +
                ", fromTable='" + fromTable + '\'' +
                ", fromColumn='" + fromColumn + '\'' +
                ", toTable='" + toTable + '\'' +
                ", toColumn='" + toColumn + '\'' +
                ", relationshipType='" + relationshipType + '\'' +
                '}';
    }
}