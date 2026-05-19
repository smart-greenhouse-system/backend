package com.proyectosu.invernadero.predictions.infrastructure.persistence.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Document(collection = "predictions")
@Getter
@Setter
public class PrediccionDocument {

    @Id
    private String id;

    @Field("tipo")
    private String tipo;

    @Field("mensaje")
    private String mensaje;

    @Field("accion_recomendada")
    private String accionRecomendada;

    @Field("device_id")
    private String deviceId;

    @Field("procesado")
    private Boolean procesado;

    @Field("created_at")
    private Instant createdAt;

    public PrediccionDocument() {
    }
}