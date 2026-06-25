package com.proyectosu.invernadero.actuator.infrastructure.outbound.persistence;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "actuator_events")
public class MongoActuatorEventDocument {
    @Id
    private String id;

    private String deviceId;

    private String actuator;

    private String action;

    private Boolean executed;

    private String origin;

    private String eventType;

    private String status;

    private String topic;

    private Integer timeAction;

    private Instant createdAt;

}
