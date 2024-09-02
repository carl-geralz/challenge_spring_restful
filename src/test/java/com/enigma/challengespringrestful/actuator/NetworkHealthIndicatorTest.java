package com.enigma.challengespringrestful.actuator;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Health;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class NetworkHealthIndicatorTest {

    @Test
    void health_whenHostIsReachable() throws IOException {
        // Mock InetAddress
        InetAddress mockInetAddress = mock(InetAddress.class);
        when(mockInetAddress.isReachable(anyInt())).thenReturn(true);

        NetworkHealthIndicator networkHealthIndicator = new NetworkHealthIndicator(mockInetAddress);
        Health health = networkHealthIndicator.health();

        assertEquals(Health.up().build(), health);
    }

    @Test
    void health_whenHostIsNotReachable() throws IOException {
        // Mock InetAddress
        InetAddress mockInetAddress = mock(InetAddress.class);
        when(mockInetAddress.isReachable(anyInt())).thenReturn(false);

        NetworkHealthIndicator networkHealthIndicator = new NetworkHealthIndicator(mockInetAddress);
        Health health = networkHealthIndicator.health();

        assertEquals(Health.down().withDetail("Error", ConstantMessage.UNREACHABLE).build(), health);
    }

    @Test
    void health_whenUnknownHostExceptionOccurs() throws IOException {
        // Mock InetAddress
        InetAddress mockInetAddress = mock(InetAddress.class);
        when(mockInetAddress.isReachable(anyInt())).thenThrow(new UnknownHostException("Test exception"));

        NetworkHealthIndicator networkHealthIndicator = new NetworkHealthIndicator(mockInetAddress);
        Health health = networkHealthIndicator.health();

        assertEquals(Health.down().withDetail("Error", "Unknown host: Test exception").build(), health);
    }

    @Test
    void health_whenIOExceptionOccurs() throws IOException {
        // Mock InetAddress
        InetAddress mockInetAddress = mock(InetAddress.class);
        when(mockInetAddress.isReachable(anyInt())).thenThrow(new IOException("Test IOException"));

        NetworkHealthIndicator networkHealthIndicator = new NetworkHealthIndicator(mockInetAddress);
        Health health = networkHealthIndicator.health();

        assertEquals(Health.down().withDetail("Error", "IOException occurred: Test IOException").build(), health);
    }

    @Test
    void health_whenUnexpectedExceptionOccurs() throws IOException {
        // Mock InetAddress
        InetAddress mockInetAddress = mock(InetAddress.class);
        when(mockInetAddress.isReachable(anyInt())).thenThrow(new RuntimeException("Test unexpected exception"));

        NetworkHealthIndicator networkHealthIndicator = new NetworkHealthIndicator(mockInetAddress);
        Health health = networkHealthIndicator.health();

        assertEquals(Health.down().withDetail("Error", "Unexpected error: Test unexpected exception").build(), health);
    }
}
