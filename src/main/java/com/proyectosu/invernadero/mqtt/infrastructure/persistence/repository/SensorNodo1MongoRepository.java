package com.proyectosu.invernadero.mqtt.infrastructure.persistence.repository;

import com.proyectosu.invernadero.mqtt.infrastructure.persistence.document.SensorNodo1Document;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SensorNodo1MongoRepository extends MongoRepository<SensorNodo1Document, String> {
    List<SensorNodo1Document> findByDeviceId(String deviceId);
}
