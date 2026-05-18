package com.proyectosu.invernadero.notifications.center.application.usecase;

import com.proyectosu.invernadero.notifications.center.domain.model.Notification;
import com.proyectosu.invernadero.notifications.center.domain.ports.NotificationRepositoryPort;

import java.util.Comparator;
import java.util.List;

public class ListNotificationsUseCase {

    private final NotificationRepositoryPort notificationRepositoryPort;

    public ListNotificationsUseCase(NotificationRepositoryPort notificationRepositoryPort) {
        this.notificationRepositoryPort = notificationRepositoryPort;
    }

    public List<Notification> ejecutar() {
        return notificationRepositoryPort.findAll().stream()
                .filter(notification -> notification.getStatus() == null || !"archived".equalsIgnoreCase(notification.getStatus()))
                .sorted(Comparator.comparing(Notification::getCreatedAt).reversed())
                .toList();
    }
}