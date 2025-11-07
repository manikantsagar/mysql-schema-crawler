# MySQL Database Schema Crawler

A Spring Boot application that crawls MySQL database schema and generates Java model classes automatically.

## Features

- ✅ Extract complete database metadata (tables, columns, relationships)
- ✅ Generate Java model classes with JPA annotations
- ✅ Support for Lombok annotations
- ✅ REST APIs for metadata extraction and model generation
- ✅ Handle complex relationships (One-to-One, One-to-Many, Many-to-Many)
- ✅ Configurable through JSON configuration

## Prerequisites

- Java 17+
- MySQL 8.0+
- Maven 3.6+

## Setup Instructions

### 1. Clone and Build

```bash
git clone <repository-url>
cd mysql-schema-crawler
mvn clean install