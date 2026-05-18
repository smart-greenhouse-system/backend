package com.proyectosu.invernadero.notifications.domain.ports;

import com.proyectosu.invernadero.notifications.domain.model.NotificationPreferences;

public interface NotificationPreferencesRepositoryPort {

    NotificationPreferences save(NotificationPreferences notificationPreferences);
}