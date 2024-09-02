package com.enigma.challengespringrestful.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DatabaseHealthIndicator implements HealthIndicator {

    private final DataSource dataSource;

    public DatabaseHealthIndicator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Health health() {
        try {
            boolean systemIsHealthy = checkDatabaseConnection();
            if (systemIsHealthy) {
                return Health.up().build();
            } else {
                return Health.down().withDetail("Error", "Database connection check failed").build();
            }
        } catch (Exception ex) {
            return Health.down().withDetail("Error", "Exception occurred: " + ex.getMessage()).build();
        }
    }

    private boolean checkDatabaseConnection() {
        try (Connection connection = dataSource.getConnection()) {
            return connection.isValid(2); // Timeout in seconds
        } catch (SQLException e) {
            return false; // Connection failed
        }
    }
}
