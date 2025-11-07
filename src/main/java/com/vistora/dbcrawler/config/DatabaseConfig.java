package com.vistora.dbcrawler.config;

import com.vistora.dbcrawler.model.config.DatabaseConfigProperties;
import com.vistora.dbcrawler.util.JsonFileReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;

@Configuration
public class DatabaseConfig {

    @Bean
    public DatabaseConfigProperties databaseConfigProperties() {
        return JsonFileReader.readConfig("db-config.json");
    }

    @Bean
    public Connection databaseConnection() throws Exception {
        DatabaseConfigProperties config = databaseConfigProperties();
        Class.forName(config.getDatabase().getDriverClassName());
        return DriverManager.getConnection(
            config.getDatabase().getUrl(),
            config.getDatabase().getUsername(),
            config.getDatabase().getPassword()
        );
    }
}