package com.proyectosu.invernadero.sensor.infrastructure.outbound.persistence;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "sensor_data")
public class MongoSensorDataDocument {

    @Id
    private String id;

    private String deviceId;

    private Map<String, Double> sensores;

    private Instant createdAt;
}
