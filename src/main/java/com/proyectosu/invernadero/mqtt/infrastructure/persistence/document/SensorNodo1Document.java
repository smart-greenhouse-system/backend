package com.proyectosu.invernadero.mqtt.infrastructure.persistence.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "sensores_nodo1")
public class SensorNodo1Document {
    @Id
    private String id;

    private String deviceId;
    private Double humedadRelativa;
    private Double humedadSuelo;
    private String timestamp;
}
