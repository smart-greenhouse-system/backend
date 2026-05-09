package com.proyectosu.invernadero.mqtt.infrastructure.persistence.repository;

import com.proyectosu.invernadero.mqtt.infrastructure.persistence.document.EstadoActuadorDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EstadoActuadorMongoRepository extends MongoRepository<EstadoActuadorDocument, String> {
    List<EstadoActuadorDocument> findByDeviceId(String deviceId);
}
