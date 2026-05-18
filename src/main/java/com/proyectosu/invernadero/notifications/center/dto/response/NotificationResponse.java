package com.proyectosu.invernadero.notifications.center.dto.response;

import com.proyectosu.invernadero.notifications.center.domain.model.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationResponse {

    private String notification_id;
    private String alert_id;
    private String title;
    private String message;
    private String severity;
    private String status;
    private String suggested_action;
    private String created_at;

    public static NotificationResponse fromDomain(Notification notification) {
        return new NotificationResponse(
                notification.getId(),
                notification.getAlertId(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getSeverity(),
                notification.getStatus(),
                notification.getSuggestedAction(),
                notification.getCreatedAt().toString()
        );
    }
}