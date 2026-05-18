package com.proyectosu.invernadero.notifications.center.infrastructure.persistence.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "notifications")
@Getter
@Setter
public class NotificationDocument {

    @Id
    private String id;

    private String alertId;
    private String title;
    private String message;
    private String severity;
    private String status;
    private String suggestedAction;
    private Instant createdAt;

    public NotificationDocument() {
    }
}