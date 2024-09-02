package com.enigma.challengespringrestful.actuator;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MemoryHealthIndicator implements HealthIndicator {

    private static final long MAX_HEAP_MEMORY_MB = 1024;
    private final MemoryMetrics memoryMetrics;

    public MemoryHealthIndicator(MemoryMetrics memoryMetrics) {
        this.memoryMetrics = memoryMetrics;
    }

    @Override
    public Health health() {
        long maxMemory = memoryMetrics.getMaxMemory() / (1024 * 1024);
        long usedMemory = memoryMetrics.getTotalMemory() / (1024 * 1024);

        if (usedMemory > MAX_HEAP_MEMORY_MB) {
            return Health.down().withDetail("Error", ConstantMessage.HIGH_MEMORY_USAGE + usedMemory + "/" + maxMemory).build();
        }
        return Health.up().build();
    }
}
