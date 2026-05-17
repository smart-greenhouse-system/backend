package com.proyectosu.invernadero.devices.infrastructure.persistence.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.List;

@Document(collection = "devices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DispositivoDocument {

    @Id
    private String id;

    @Field("device_id")
    private String deviceId;

    @Field("nombre")
    private String nombre;

    @Field("tipo")
    private String tipo;

    @Field("estado")
    private String estado;

    @Field("sensores")
    private List<String> sensores;

    @Field("actuadores")
    private List<String> actuadores;

    @Field("created_at")
    private Instant createdAt;
}