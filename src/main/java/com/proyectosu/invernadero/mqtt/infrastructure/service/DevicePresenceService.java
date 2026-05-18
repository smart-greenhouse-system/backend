package com.proyectosu.invernadero.mqtt.infrastructure.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DevicePresenceService {

    private final Map<String, Instant> lastSeenByDevice = new ConcurrentHashMap<>();
    private final Map<String, String> statusByDevice = new ConcurrentHashMap<>();
    private final Duration offlineThreshold;

    public DevicePresenceService(
            @Value("${mqtt.device-offline-threshold-seconds:120}") long offlineThresholdSeconds
    ) {
        this.offlineThreshold = Duration.ofSeconds(offlineThresholdSeconds);
    }

    public void markOnline(String deviceId) {
        lastSeenByDevice.put(deviceId, Instant.now());
        statusByDevice.put(deviceId, "ONLINE");
    }

    public void markOffline(String deviceId) {
        lastSeenByDevice.putIfAbsent(deviceId, Instant.now());
        statusByDevice.put(deviceId, "OFFLINE");
    }

    public String getCurrentStatus(String deviceId) {
        Instant lastSeen = lastSeenByDevice.get(deviceId);
        if (lastSeen == null) {
            return "UNKNOWN";
        }
        if (Duration.between(lastSeen, Instant.now()).compareTo(offlineThreshold) > 0) {
            statusByDevice.put(deviceId, "OFFLINE");
        }
        return statusByDevice.getOrDefault(deviceId, "UNKNOWN");
    }
}
