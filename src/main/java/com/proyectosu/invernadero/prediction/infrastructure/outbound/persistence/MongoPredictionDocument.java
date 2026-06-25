package com.proyectosu.invernadero.prediction.infrastructure.outbound.persistence;

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
@Document(collection = "predictions")
public class MongoPredictionDocument {
    @Id
    private String id;

    private String tipo;

    @Field("device_id")
    private String deviceId;

    private String actuatorId;

    private Integer timeAction;

    private Boolean processed;

    private Boolean automaticMode;

    private Boolean executed;

    private String cultivo;

    private Boolean success;

    @Field("estado_planta")
    private String estadoPlanta;

    private Double confianza;

    @Field("tiempo_cosecha_dias")
    private Integer tiempoCosechaDias;

    @Field("created_at")
    private Instant createdAt;
}
