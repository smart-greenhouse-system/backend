package com.proyectosu.invernadero.mqtt.infrastructure.persistence.repository;

import com.proyectosu.invernadero.mqtt.infrastructure.persistence.document.SensorNodo2Document;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SensorNodo2MongoRepository extends MongoRepository<SensorNodo2Document, String> {
    List<SensorNodo2Document> findByDeviceId(String deviceId);
}
