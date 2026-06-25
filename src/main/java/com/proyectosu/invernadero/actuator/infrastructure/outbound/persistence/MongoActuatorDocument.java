package com.proyectosu.invernadero.actuator.infrastructure.outbound.persistence;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "actuators")
public class MongoActuatorDocument {

    @Id
    @Field("actuator_id")
    private String actuatorId;

    @Field("device_id")
    private String deviceId;

    private String actuador;

    private String nombre;

    private String estado;

    private Boolean enabled;

    @Field("created_at")
    private Instant createdAt;

    @Field("updated_at")
    private Instant updatedAt;
}
