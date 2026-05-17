package com.proyectosu.invernadero.actuators.infrastructure.persistence.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Document(collection = "actuator_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActuatorEventDocument {

    @Id
    private String id;

    @Field("device_id")
    private String deviceId;

    @Field("actuador")
    private String actuador;

    @Field("accion")
    private String accion;

    @Field("topic")
    private String topic;

    @Field("estado")
    private String estado;

    @Field("timestamp")
    private Instant timestamp;
}