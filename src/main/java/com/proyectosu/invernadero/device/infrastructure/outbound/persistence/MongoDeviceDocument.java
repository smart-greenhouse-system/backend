package com.proyectosu.invernadero.device.infrastructure.outbound.persistence;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "devices")
public class MongoDeviceDocument {

    @Id
    @Field("device_id")
    private String deviceId;

    private String nombre;

    private String tipo;

    private String estado;

    private List<String> sensores;

    private List<String> actuadores;

    @Field("last_seen")
    private Instant lastSeen;

    @Field("created_at")
    private Instant createdAt;
}
