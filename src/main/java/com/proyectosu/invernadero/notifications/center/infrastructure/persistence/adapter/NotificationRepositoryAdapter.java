package com.proyectosu.invernadero.notifications.center.infrastructure.persistence.adapter;

import com.proyectosu.invernadero.notifications.center.domain.model.Notification;
import com.proyectosu.invernadero.notifications.center.domain.ports.NotificationRepositoryPort;
import com.proyectosu.invernadero.notifications.center.infrastructure.persistence.document.NotificationDocument;
import com.proyectosu.invernadero.notifications.center.infrastructure.persistence.repository.NotificationMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NotificationRepositoryAdapter implements NotificationRepositoryPort {

    private final NotificationMongoRepository repository;

    @Override
    public Notification save(Notification notification) {
        NotificationDocument saved = repository.save(toDocument(notification));
        return toDomain(saved);
    }

    @Override
    public List<Notification> findAll() {
        return repository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<Notification> findById(String notificationId) {
        return repository.findById(notificationId).map(this::toDomain);
    }

    @Override
    public void deleteById(String notificationId) {
        repository.deleteById(notificationId);
    }

    private NotificationDocument toDocument(Notification notification) {
        NotificationDocument document = new NotificationDocument();
        document.setId(notification.getId());
        document.setAlertId(notification.getAlertId());
        document.setTitle(notification.getTitle());
        document.setMessage(notification.getMessage());
        document.setSeverity(notification.getSeverity());
        document.setStatus(notification.getStatus());
        document.setSuggestedAction(notification.getSuggestedAction());
        document.setCreatedAt(notification.getCreatedAt());
        return document;
    }

    private Notification toDomain(NotificationDocument document) {
        return new Notification(
                document.getId(),
                document.getAlertId(),
                document.getTitle(),
                document.getMessage(),
                document.getSeverity(),
                document.getStatus(),
                document.getSuggestedAction(),
                document.getCreatedAt()
        );
    }
}