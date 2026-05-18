package com.proyectosu.invernadero.notifications.infrastructure.persistence.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "notification_preferences")
@Getter
@Setter
public class NotificationPreferencesDocument {

    @Id
    private String id;

    @Indexed(unique = true)
    private String userId;

    private boolean criticalAlerts;
    private boolean warnings;
    private boolean offlineSensors;
    private boolean actuatorFailures;
    private boolean pushEnabled;
    private boolean emailEnabled;
    private boolean inAppEnabled;
    private boolean doNotDisturbEnabled;
    private String doNotDisturbStartTime;
    private String doNotDisturbEndTime;
    private Instant updatedAt;

    public NotificationPreferencesDocument() {
    }
}