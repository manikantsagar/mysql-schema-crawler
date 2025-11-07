package com.vistora.dbcrawler.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vistora.dbcrawler.model.config.DatabaseConfigProperties;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;

public class JsonFileReader {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static DatabaseConfigProperties readConfig(String filename) {
        try {
            ClassPathResource resource = new ClassPathResource(filename);
            InputStream inputStream = resource.getInputStream();
            return objectMapper.readValue(inputStream, DatabaseConfigProperties.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read configuration file: " + filename, e);
        }
    }
}