package com.enigma.challengespringrestful.actuator;

public interface MemoryMetrics {
    long getMaxMemory();

    long getTotalMemory();
}
