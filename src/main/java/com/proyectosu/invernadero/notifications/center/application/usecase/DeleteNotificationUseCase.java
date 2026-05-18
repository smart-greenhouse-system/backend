package com.proyectosu.invernadero.notifications.center.application.usecase;

import com.proyectosu.invernadero.notifications.center.domain.ports.NotificationRepositoryPort;
import com.proyectosu.invernadero.shared.errores.ApiException;
import org.springframework.http.HttpStatus;

public class DeleteNotificationUseCase {

    private final NotificationRepositoryPort notificationRepositoryPort;

    public DeleteNotificationUseCase(NotificationRepositoryPort notificationRepositoryPort) {
        this.notificationRepositoryPort = notificationRepositoryPort;
    }

    public void ejecutar(String notificationId) {
        String normalizedNotificationId = normalizeRequiredValue(notificationId, "notification_id es obligatorio");

        notificationRepositoryPort.findById(normalizedNotificationId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "NOTIFICATION_NOT_FOUND", "Notification not found"));

        notificationRepositoryPort.deleteById(normalizedNotificationId);
    }

    private String normalizeRequiredValue(String value, String errorMessage) {
        String normalized = value == null ? "" : value.trim();

        if (normalized.isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "INVALID_NOTIFICATION_ID", errorMessage);
        }

        return normalized;
    }
}