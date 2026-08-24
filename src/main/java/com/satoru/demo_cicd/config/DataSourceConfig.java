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

    @Bean
    @Primary
    public DataSource dataSource(Environment env) {
        // Try common env names
        String raw = env.getProperty("DATABASE_URL");
        if (raw == null || raw.isBlank()) {
            raw = env.getProperty("SPRING_DATASOURCE_URL");
        }

        String url = normalizeJdbcUrl(raw);

        String username = firstNonNull(env.getProperty("PGUSER"), env.getProperty("SPRING_DATASOURCE_USERNAME"));
        String password = firstNonNull(env.getProperty("PGPASSWORD"), env.getProperty("SPRING_DATASOURCE_PASSWORD"));
        String driver = firstNonNull(env.getProperty("DATABASE_DRIVER_CLASS_NAME"), env.getProperty("spring.datasource.driver-class-name", "org.postgresql.Driver"));

        // If no full URL provided, try to build from PGHOST/PGPORT/PGDATABASE
        if (url == null) {
            String host = firstNonNull(env.getProperty("PGHOST"), env.getProperty("POSTGRES_HOST"));
            String port = firstNonNull(env.getProperty("PGPORT"), env.getProperty("POSTGRES_PORT"));
            String db = firstNonNull(env.getProperty("PGDATABASE"), env.getProperty("POSTGRES_DB"));
            if (host != null) {
                if (port == null) port = "5432";
                if (db == null) db = "demo_cicd";
                url = "jdbc:postgresql://" + host + ":" + port + "/" + db;
            }
        }

        if (url == null) {
            // Fallback to localhost Postgres if nothing provided (useful for CI with service mapping)
            url = "jdbc:postgresql://localhost:5432/demo_cicd";
            // set defaults consistent with local docker-compose and CI
            if (username == null) username = "postgres";
            if (password == null) password = "postgres";
        }

        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(url);
        if (username != null) ds.setUsername(username);
        if (password != null) ds.setPassword(password);
        ds.setDriverClassName(driver);
        return ds;
    }

    private String normalizeJdbcUrl(@Nullable String raw) {
        if (raw == null || raw.isBlank()) return null;
        raw = raw.trim();
        // If already starts with jdbc:, assume correct
        if (raw.startsWith("jdbc:")) return raw;
        // If starts with postgresql:// or postgres:// add jdbc:
        if (raw.startsWith("postgresql://") || raw.startsWith("postgres://")) {
            return "jdbc:" + raw;
        }
        // Otherwise return as-is
        return raw;
    }

    private String firstNonNull(@Nullable String a, @Nullable String b) {
        if (a != null && !a.isBlank()) return a;
        if (b != null && !b.isBlank()) return b;
        return null;
    }
}
