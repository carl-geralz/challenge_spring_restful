package com.enigma.challengespringrestful.actuator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.actuate.health.Health;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

class DatabaseHealthIndicatorTest {

    private DataSource dataSource;
    private DatabaseHealthIndicator healthIndicator;

    @BeforeEach
    void setUp() {
        dataSource = Mockito.mock(DataSource.class);
        healthIndicator = new DatabaseHealthIndicator(dataSource);
    }

    @AfterEach
    void tearDown() {
        dataSource = null;
        healthIndicator = null;
    }

    @Test
    void health_whenDatabaseIsUp() throws SQLException {
        Connection connection = Mockito.mock(Connection.class);
        when(connection.isValid(anyInt())).thenReturn(true);
        when(dataSource.getConnection()).thenReturn(connection);

        Health health = healthIndicator.health();

        assertEquals(Health.up().build(), health);
    }

    @Test
    void health_whenDatabaseIsDown() throws SQLException {
        Connection connection = Mockito.mock(Connection.class);
        when(connection.isValid(anyInt())).thenReturn(false);
        when(dataSource.getConnection()).thenReturn(connection);

        Health health = healthIndicator.health();

        assertEquals(Health.down()
                .withDetail("Error", "Database connection check failed")
                .build(), health);
    }

}
