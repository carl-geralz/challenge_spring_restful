package com.enigma.challengespringrestful.actuator;

import org.springframework.stereotype.Component;

@Component
public class RealMemoryMetrics implements MemoryMetrics {

    @Override
    public long getMaxMemory() {
        return Runtime.getRuntime().maxMemory();
    }

    @Override
    public long getTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }
}

