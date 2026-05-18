package com.proyectosu.invernadero.notifications.infrastructure.persistence.adapter;

import com.proyectosu.invernadero.notifications.domain.model.NotificationPreferences;
import com.proyectosu.invernadero.notifications.domain.ports.NotificationPreferencesRepositoryPort;
import com.proyectosu.invernadero.notifications.infrastructure.persistence.document.NotificationPreferencesDocument;
import com.proyectosu.invernadero.notifications.infrastructure.persistence.repository.NotificationPreferencesMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationPreferencesRepositoryAdapter implements NotificationPreferencesRepositoryPort {

    private final NotificationPreferencesMongoRepository repository;

    @Override
    public NotificationPreferences save(NotificationPreferences notificationPreferences) {
        NotificationPreferencesDocument document = repository.findByUserId(notificationPreferences.getUserId())
                .map(existing -> updateDocument(existing, notificationPreferences))
                .orElseGet(() -> toDocument(notificationPreferences));

        NotificationPreferencesDocument saved = repository.save(document);
        return toDomain(saved);
    }

    private NotificationPreferencesDocument updateDocument(
            NotificationPreferencesDocument document,
            NotificationPreferences notificationPreferences
    ) {
        document.setUserId(notificationPreferences.getUserId());
        document.setCriticalAlerts(notificationPreferences.isCriticalAlerts());
        document.setWarnings(notificationPreferences.isWarnings());
        document.setOfflineSensors(notificationPreferences.isOfflineSensors());
        document.setActuatorFailures(notificationPreferences.isActuatorFailures());
        document.setPushEnabled(notificationPreferences.isPushEnabled());
        document.setEmailEnabled(notificationPreferences.isEmailEnabled());
        document.setInAppEnabled(notificationPreferences.isInAppEnabled());
        document.setDoNotDisturbEnabled(notificationPreferences.isDoNotDisturbEnabled());
        document.setDoNotDisturbStartTime(notificationPreferences.getDoNotDisturbStartTime());
        document.setDoNotDisturbEndTime(notificationPreferences.getDoNotDisturbEndTime());
        document.setUpdatedAt(notificationPreferences.getUpdatedAt());
        return document;
    }

    private NotificationPreferencesDocument toDocument(NotificationPreferences notificationPreferences) {
        NotificationPreferencesDocument document = new NotificationPreferencesDocument();
        document.setUserId(notificationPreferences.getUserId());
        document.setCriticalAlerts(notificationPreferences.isCriticalAlerts());
        document.setWarnings(notificationPreferences.isWarnings());
        document.setOfflineSensors(notificationPreferences.isOfflineSensors());
        document.setActuatorFailures(notificationPreferences.isActuatorFailures());
        document.setPushEnabled(notificationPreferences.isPushEnabled());
        document.setEmailEnabled(notificationPreferences.isEmailEnabled());
        document.setInAppEnabled(notificationPreferences.isInAppEnabled());
        document.setDoNotDisturbEnabled(notificationPreferences.isDoNotDisturbEnabled());
        document.setDoNotDisturbStartTime(notificationPreferences.getDoNotDisturbStartTime());
        document.setDoNotDisturbEndTime(notificationPreferences.getDoNotDisturbEndTime());
        document.setUpdatedAt(notificationPreferences.getUpdatedAt());
        return document;
    }

    private NotificationPreferences toDomain(NotificationPreferencesDocument document) {
        return new NotificationPreferences(
                document.getUserId(),
                document.isCriticalAlerts(),
                document.isWarnings(),
                document.isOfflineSensors(),
                document.isActuatorFailures(),
                document.isPushEnabled(),
                document.isEmailEnabled(),
                document.isInAppEnabled(),
                document.isDoNotDisturbEnabled(),
                document.getDoNotDisturbStartTime(),
                document.getDoNotDisturbEndTime(),
                document.getUpdatedAt()
        );
    }
}