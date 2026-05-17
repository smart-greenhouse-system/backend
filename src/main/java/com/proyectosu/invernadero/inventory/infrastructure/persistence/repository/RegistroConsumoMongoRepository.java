package com.proyectosu.invernadero.inventory.infrastructure.persistence.repository;

import com.proyectosu.invernadero.inventory.infrastructure.persistence.document.RegistroConsumoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegistroConsumoMongoRepository extends MongoRepository<RegistroConsumoDocument, String> {
}