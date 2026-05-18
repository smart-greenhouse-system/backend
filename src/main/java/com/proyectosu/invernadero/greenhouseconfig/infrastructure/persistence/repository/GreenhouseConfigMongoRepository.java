package com.proyectosu.invernadero.greenhouseconfig.infrastructure.persistence.repository;

import com.proyectosu.invernadero.greenhouseconfig.infrastructure.persistence.document.GreenhouseConfigDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GreenhouseConfigMongoRepository extends MongoRepository<GreenhouseConfigDocument, String> {

    Optional<GreenhouseConfigDocument> findByConfigKey(String configKey);
}