package com.proyectosu.invernadero.notifications.center.infrastructure.persistence.repository;

import com.proyectosu.invernadero.notifications.center.infrastructure.persistence.document.NotificationDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationMongoRepository extends MongoRepository<NotificationDocument, String> {
}