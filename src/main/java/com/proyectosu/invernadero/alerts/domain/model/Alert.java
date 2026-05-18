package com.proyectosu.invernadero.alerts.domain.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
public class Alert {

    private final String id;
    private final String greenhouseId;
    private final String sensorReadingId;
    private final String thresholdRuleId;
    private final String source;
    private final String description;
    private final String severity;
    private final BigDecimal value;
    private final String notificationStatus;
    private final Instant timestamp;

    public Alert(
            String id,
            String greenhouseId,
            String sensorReadingId,
            String thresholdRuleId,
            String source,
            String description,
            String severity,
            BigDecimal value,
            String notificationStatus,
            Instant timestamp
    ) {
        this.id = id;
        this.greenhouseId = greenhouseId;
        this.sensorReadingId = sensorReadingId;
        this.thresholdRuleId = thresholdRuleId;
        this.source = source;
        this.description = description;
        this.severity = severity;
        this.value = value;
        this.notificationStatus = notificationStatus;
        this.timestamp = timestamp;
    }

    public Alert withNotificationStatus(String status) {
        return new Alert(
                id,
                greenhouseId,
                sensorReadingId,
                thresholdRuleId,
                source,
                description,
                severity,
                value,
                status,
                timestamp
        );
    }
}