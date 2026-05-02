package com.proyectosu.invernadero.infraestructure.persistencenorel.repository;

import com.proyectosu.invernadero.infraestructure.persistencenorel.document.EventoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventoMongoRepository extends MongoRepository<EventoDocument, String> {
}
