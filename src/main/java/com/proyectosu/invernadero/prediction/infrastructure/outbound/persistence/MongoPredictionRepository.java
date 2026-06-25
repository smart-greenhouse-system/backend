package com.proyectosu.invernadero.prediction.infrastructure.outbound.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MongoPredictionRepository extends MongoRepository<MongoPredictionDocument, String> {

    Optional<MongoPredictionDocument> findFirstByTipoOrderByCreatedAtDesc(String tipo);

    List<MongoPredictionDocument> findByTipoOrderByCreatedAtDesc(String tipo);
}
