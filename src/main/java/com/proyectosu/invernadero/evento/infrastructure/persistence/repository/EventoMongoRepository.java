package com.proyectosu.invernadero.auth.infraestructure.persistencenorel.repository;

import com.proyectosu.invernadero.evento.infrastructure.persistence.document.EventoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventoMongoRepository extends MongoRepository<EventoDocument, String> {
}
