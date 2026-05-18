package com.proyectosu.invernadero.notifications.center.application.usecase;

import com.proyectosu.invernadero.notifications.center.domain.model.Notification;
import com.proyectosu.invernadero.notifications.center.domain.ports.NotificationRepositoryPort;
import com.proyectosu.invernadero.shared.errores.ApiException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Locale;

public class UpdateNotificationStatusUseCase {

    private static final List<String> VALID_STATUSES = List.of("read", "archived");

    private final NotificationRepositoryPort notificationRepositoryPort;

    public UpdateNotificationStatusUseCase(NotificationRepositoryPort notificationRepositoryPort) {
        this.notificationRepositoryPort = notificationRepositoryPort;
    }

    public Notification ejecutar(String notificationId, String status) {
        String normalizedNotificationId = normalizeRequiredValue(notificationId, "notification_id es obligatorio");
        String normalizedStatus = normalizeStatus(status);

        Notification notification = notificationRepositoryPort.findById(normalizedNotificationId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "NOTIFICATION_NOT_FOUND", "Notification not found"));

        Notification updated = notification.withStatus(normalizedStatus);
        return notificationRepositoryPort.save(updated);
    }

    private String normalizeStatus(String value) {
        String normalized = normalizeRequiredValue(value, "status es obligatorio").toLowerCase(Locale.ROOT);

        if (!VALID_STATUSES.contains(normalized)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "INVALID_NOTIFICATION_STATUS", "status is invalid");
        }

        return normalized;
    }

    private String normalizeRequiredValue(String value, String errorMessage) {
        String normalized = value == null ? "" : value.trim();

        if (normalized.isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "INVALID_NOTIFICATION_ID", errorMessage);
        }

        return normalized;
    }
}