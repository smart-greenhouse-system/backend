package com.proyectosu.invernadero.notifications.center.domain.ports;

import com.proyectosu.invernadero.notifications.center.domain.model.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationRepositoryPort {

    Notification save(Notification notification);

    List<Notification> findAll();

    Optional<Notification> findById(String notificationId);

    void deleteById(String notificationId);
}