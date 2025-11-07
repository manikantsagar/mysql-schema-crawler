package com.vistora.dbcrawler.util;

public class TypeConverter {
    
    public static String sqlTypeToJavaType(String sqlType, boolean useWrapperTypes) {
        if (sqlType == null) {
            return "Object";
        }
        
        String lowerSqlType = sqlType.toLowerCase();
        
        switch (lowerSqlType) {
            case "varchar":
            case "char":
            case "text":
            case "longtext":
            case "mediumtext":
            case "tinytext":
                return "String";
                
            case "int":
            case "integer":
            case "tinyint":
            case "smallint":
            case "mediumint":
                return useWrapperTypes ? "Integer" : "int";
                
            case "bigint":
                return useWrapperTypes ? "Long" : "long";
                
            case "decimal":
            case "numeric":
            case "float":
                return useWrapperTypes ? "Float" : "float";
                
            case "double":
                return useWrapperTypes ? "Double" : "double";
                
            case "boolean":
            case "bit":
                return useWrapperTypes ? "Boolean" : "boolean";
                
            case "date":
            case "datetime":
            case "timestamp":
            case "time":
                return "java.time.LocalDateTime";
                
            case "blob":
            case "longblob":
            case "mediumblob":
            case "tinyblob":
                return "byte[]";
                
            default:
                return "Object";
        }
    }
    
    public static String toCamelCase(String columnName) {
        if (columnName == null || columnName.isEmpty()) {
            return columnName;
        }
        
        String[] parts = columnName.split("_");
        StringBuilder result = new StringBuilder(parts[0].toLowerCase());
        
        for (int i = 1; i < parts.length; i++) {
            if (!parts[i].isEmpty()) {
                result.append(parts[i].substring(0, 1).toUpperCase())
                      .append(parts[i].substring(1).toLowerCase());
            }
        }
        
        return result.toString();
    }
    
    public static String toPascalCase(String tableName) {
        if (tableName == null || tableName.isEmpty()) {
            return tableName;
        }
        
        String camelCase = toCamelCase(tableName);
        return camelCase.substring(0, 1).toUpperCase() + camelCase.substring(1);
    }
}