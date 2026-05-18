package com.proyectosu.invernadero.notifications.center.inbound.controller;

import com.proyectosu.invernadero.notifications.center.application.usecase.DeleteNotificationUseCase;
import com.proyectosu.invernadero.notifications.center.application.usecase.ListNotificationsUseCase;
import com.proyectosu.invernadero.notifications.center.application.usecase.UpdateNotificationStatusUseCase;
import com.proyectosu.invernadero.notifications.center.domain.model.Notification;
import com.proyectosu.invernadero.notifications.center.dto.request.UpdateNotificationStatusRequest;
import com.proyectosu.invernadero.notifications.center.dto.response.NotificationActionResponse;
import com.proyectosu.invernadero.notifications.center.dto.response.NotificationResponse;
import com.proyectosu.invernadero.notifications.center.dto.response.NotificationsListResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationsController {

    private final ListNotificationsUseCase listNotificationsUseCase;
    private final UpdateNotificationStatusUseCase updateNotificationStatusUseCase;
    private final DeleteNotificationUseCase deleteNotificationUseCase;

    @GetMapping
    public ResponseEntity<NotificationsListResponse> list() {
        List<Notification> notifications = listNotificationsUseCase.ejecutar();
        List<NotificationResponse> responseNotifications = notifications.stream()
                .map(NotificationResponse::fromDomain)
                .toList();

        return ResponseEntity.ok(new NotificationsListResponse(responseNotifications));
    }

    @PatchMapping("/{notification_id}")
    public ResponseEntity<NotificationResponse> updateStatus(
            @PathVariable("notification_id") String notificationId,
            @Valid @RequestBody UpdateNotificationStatusRequest request
    ) {
        Notification notification = updateNotificationStatusUseCase.ejecutar(notificationId, request.getStatus());
        return ResponseEntity.ok(NotificationResponse.fromDomain(notification));
    }

    @DeleteMapping("/{notification_id}")
    public ResponseEntity<NotificationActionResponse> delete(@PathVariable("notification_id") String notificationId) {
        deleteNotificationUseCase.ejecutar(notificationId);
        return ResponseEntity.ok(new NotificationActionResponse(
                "Notification deleted successfully",
                notificationId
        ));
    }
}