package com.proyectosu.invernadero.notifications.infrastructure.persistence.repository;

import com.proyectosu.invernadero.notifications.infrastructure.persistence.document.NotificationPreferencesDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface NotificationPreferencesMongoRepository extends MongoRepository<NotificationPreferencesDocument, String> {

    Optional<NotificationPreferencesDocument> findByUserId(String userId);
}