package com.proyectosu.invernadero.notifications.domain.model;

import lombok.Getter;

import java.time.Instant;

@Getter
public class NotificationPreferences {

    private final String userId;
    private final boolean criticalAlerts;
    private final boolean warnings;
    private final boolean offlineSensors;
    private final boolean actuatorFailures;
    private final boolean pushEnabled;
    private final boolean emailEnabled;
    private final boolean inAppEnabled;
    private final boolean doNotDisturbEnabled;
    private final String doNotDisturbStartTime;
    private final String doNotDisturbEndTime;
    private final Instant updatedAt;

    public NotificationPreferences(
            String userId,
            boolean criticalAlerts,
            boolean warnings,
            boolean offlineSensors,
            boolean actuatorFailures,
            boolean pushEnabled,
            boolean emailEnabled,
            boolean inAppEnabled,
            boolean doNotDisturbEnabled,
            String doNotDisturbStartTime,
            String doNotDisturbEndTime,
            Instant updatedAt
    ) {
        this.userId = userId;
        this.criticalAlerts = criticalAlerts;
        this.warnings = warnings;
        this.offlineSensors = offlineSensors;
        this.actuatorFailures = actuatorFailures;
        this.pushEnabled = pushEnabled;
        this.emailEnabled = emailEnabled;
        this.inAppEnabled = inAppEnabled;
        this.doNotDisturbEnabled = doNotDisturbEnabled;
        this.doNotDisturbStartTime = doNotDisturbStartTime;
        this.doNotDisturbEndTime = doNotDisturbEndTime;
        this.updatedAt = updatedAt;
    }
}