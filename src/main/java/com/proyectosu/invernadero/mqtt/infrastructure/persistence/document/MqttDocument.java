package com.proyectosu.invernadero.mqtt.infrastructure.persistence.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "mqtt")
@Getter
@AllArgsConstructor
public class MqttDocument {
    @Id
    private String id;

    private String topico;
    private String mensaje;
}
