package com.vistora.dbcrawler.model.config;

public class DatabaseConfigProperties {
    private DatabaseConfig database;
    private CrawlerConfig crawler;

    public static class DatabaseConfig {
        private String url;
        private String username;
        private String password;
        private String driverClassName;

        // Getters and Setters
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getDriverClassName() { return driverClassName; }
        public void setDriverClassName(String driverClassName) { this.driverClassName = driverClassName; }
    }

    public static class CrawlerConfig {
        private String schemaName;
        private String modelPackage;
        private boolean generateLombokAnnotations;
        private boolean generateJpaAnnotations;

        // Getters and Setters
        public String getSchemaName() { return schemaName; }
        public void setSchemaName(String schemaName) { this.schemaName = schemaName; }
        public String getModelPackage() { return modelPackage; }
        public void setModelPackage(String modelPackage) { this.modelPackage = modelPackage; }
        public boolean isGenerateLombokAnnotations() { return generateLombokAnnotations; }
        public void setGenerateLombokAnnotations(boolean generateLombokAnnotations) { this.generateLombokAnnotations = generateLombokAnnotations; }
        public boolean isGenerateJpaAnnotations() { return generateJpaAnnotations; }
        public void setGenerateJpaAnnotations(boolean generateJpaAnnotations) { this.generateJpaAnnotations = generateJpaAnnotations; }
    }

    // Getters and Setters for main class
    public DatabaseConfig getDatabase() { return database; }
    public void setDatabase(DatabaseConfig database) { this.database = database; }
    public CrawlerConfig getCrawler() { return crawler; }
    public void setCrawler(CrawlerConfig crawler) { this.crawler = crawler; }
}