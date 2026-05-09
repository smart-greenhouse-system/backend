package com.proyectosu.invernadero.mqtt.infrastructure.persistence.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "sensores_nodo2")
public class SensorNodo2Document {
    @Id
    private String id;

    private String deviceId;
    private Double temperatura;
    private Double iluminacion;
    private String timestamp;
}
