package com.enigma.challengespringrestful.actuator;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class NetworkHealthIndicator implements HealthIndicator {

    private final InetAddress inetAddress;

    public NetworkHealthIndicator(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    @Override
    public Health health() {
        try {
            if (inetAddress.isReachable(30000)) { // 30 seconds timeout
                return Health.up().build();
            } else {
                return Health.down().withDetail("Error", ConstantMessage.UNREACHABLE).build();
            }
        } catch (UnknownHostException e) {
            return Health.down().withDetail("Error", "Unknown host: " + e.getMessage()).build();
        } catch (IOException e) {
            return Health.down().withDetail("Error", "IOException occurred: " + e.getMessage()).build();
        } catch (Exception e) {
            return Health.down().withDetail("Error", "Unexpected error: " + e.getMessage()).build();
        }
    }
}
