package com.proyectosu.invernadero.notifications.center.domain.model;

import lombok.Getter;

import java.time.Instant;

@Getter
public class Notification {

    private final String id;
    private final String alertId;
    private final String title;
    private final String message;
    private final String severity;
    private final String status;
    private final String suggestedAction;
    private final Instant createdAt;

    public Notification(
            String id,
            String alertId,
            String title,
            String message,
            String severity,
            String status,
            String suggestedAction,
            Instant createdAt
    ) {
        this.id = id;
        this.alertId = alertId;
        this.title = title;
        this.message = message;
        this.severity = severity;
        this.status = status;
        this.suggestedAction = suggestedAction;
        this.createdAt = createdAt;
    }

    public Notification withStatus(String status) {
        return new Notification(
                id,
                alertId,
                title,
                message,
                severity,
                status,
                suggestedAction,
                createdAt
        );
    }
}