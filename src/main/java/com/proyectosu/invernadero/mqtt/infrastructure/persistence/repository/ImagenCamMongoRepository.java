package com.proyectosu.invernadero.mqtt.infrastructure.persistence.repository;

import com.proyectosu.invernadero.mqtt.infrastructure.persistence.document.ImagenCamDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImagenCamMongoRepository extends MongoRepository<ImagenCamDocument, String> {
}