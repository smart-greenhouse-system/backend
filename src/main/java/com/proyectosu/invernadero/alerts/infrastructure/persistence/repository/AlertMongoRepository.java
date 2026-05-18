package com.proyectosu.invernadero.alerts.infrastructure.persistence.repository;

import com.proyectosu.invernadero.alerts.infrastructure.persistence.document.AlertDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlertMongoRepository extends MongoRepository<AlertDocument, String> {
}