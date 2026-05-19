package com.proyectosu.invernadero.predictions.infrastructure.persistence.repository;

import com.proyectosu.invernadero.predictions.infrastructure.persistence.document.PrediccionDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PrediccionMongoRepository extends MongoRepository<PrediccionDocument, String> {

    List<PrediccionDocument> findAllByOrderByCreatedAtDesc();
}