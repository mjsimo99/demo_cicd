package com.satoru.demo_cicd.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.Nullable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    // Custom DataSourceConfig removed. Rely on Spring Boot auto-configuration or the application's startup guard.
}
