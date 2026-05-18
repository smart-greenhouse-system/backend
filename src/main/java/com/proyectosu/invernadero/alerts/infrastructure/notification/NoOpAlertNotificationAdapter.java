package com.proyectosu.invernadero.alerts.infrastructure.notification;

import com.proyectosu.invernadero.alerts.domain.model.Alert;
import com.proyectosu.invernadero.alerts.domain.ports.AlertNotificationPort;
import com.proyectosu.invernadero.notifications.center.domain.model.Notification;
import com.proyectosu.invernadero.notifications.center.domain.ports.NotificationRepositoryPort;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

@Component
public class NoOpAlertNotificationAdapter implements AlertNotificationPort {

    private final NotificationRepositoryPort notificationRepositoryPort;

    public NoOpAlertNotificationAdapter(NotificationRepositoryPort notificationRepositoryPort) {
        this.notificationRepositoryPort = notificationRepositoryPort;
    }

    @Override
    public void send(Alert alert) {
        Notification notification = new Notification(
                UUID.randomUUID().toString(),
                alert.getId(),
                buildTitle(alert.getSeverity()),
                alert.getDescription(),
                alert.getSeverity(),
                "unread",
                buildSuggestedAction(alert.getSeverity()),
                alert.getTimestamp() == null ? Instant.now() : alert.getTimestamp()
        );

        notificationRepositoryPort.save(notification);
    }

    private String buildTitle(String severity) {
        String normalizedSeverity = severity == null ? "warning" : severity.toLowerCase(Locale.ROOT);
        if ("critical".equals(normalizedSeverity)) {
            return "Critical threshold alert";
        }

        return "Threshold alert";
    }

    private String buildSuggestedAction(String severity) {
        String normalizedSeverity = severity == null ? "warning" : severity.toLowerCase(Locale.ROOT);
        if ("critical".equals(normalizedSeverity)) {
            return "Immediate review required";
        }

        return "Monitor the greenhouse";
    }
}