package com.proyectosu.invernadero.mqtt.infrastructure.persistence.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "estados_actuadores")
public class EstadoActuadorDocument {
    @Id
    private String id;

    private String deviceId;
    private String actuador;
    private String estado;
    private Boolean ejecutado;
    private String timestamp;
}
