package com.enigma.challengespringrestful.actuator;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Health;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MemoryHealthIndicatorTest {

    private MemoryHealthIndicator memoryHealthIndicator;
    private MemoryMetrics memoryMetrics;

    @BeforeEach
    void setUp() {
        memoryMetrics = mock(MemoryMetrics.class);
        memoryHealthIndicator = new MemoryHealthIndicator(memoryMetrics);
    }

    @AfterEach
    void tearDown() {
        memoryMetrics = null;
        memoryHealthIndicator = null;
    }

    @Test
    void health_whenMemoryUsageIsWithinLimits() {
        // Mock memory metrics to simulate memory within limits
        when(memoryMetrics.getMaxMemory()).thenReturn(1024L * 1024 * 1024); // 1 GB
        when(memoryMetrics.getTotalMemory()).thenReturn(512L * 1024 * 1024); // 512 MB

        Health health = memoryHealthIndicator.health();

        assertEquals(Health.up().build(), health);
    }

    @Test
    void health_whenMemoryUsageExceedsLimits() {
        // Mock memory metrics to simulate memory usage exceeding limits
        when(memoryMetrics.getMaxMemory()).thenReturn(1024L * 1024 * 1024); // 1 GB
        when(memoryMetrics.getTotalMemory()).thenReturn(2L * 1024 * 1024 * 1024); // 2 GB

        // Convert bytes to MB for assertion
        long usedMemoryMB = 2L * 1024; // 2 GB in MB
        long maxMemoryMB = 1024L; // 1 GB in MB

        Health health = memoryHealthIndicator.health();

        assertEquals(Health.down()
                .withDetail("Error", ConstantMessage.HIGH_MEMORY_USAGE + usedMemoryMB + "/" + maxMemoryMB)
                .build(), health);
    }
}
