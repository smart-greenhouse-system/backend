package com.proyectosu.invernadero.inventory.infrastructure.persistence.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Document(collection = "inventory")
@Getter
@Setter
public class ItemInventarioDocument {

    @Id
    private String id;

    @Field("nombre")
    private String nombre;

    @Field("cantidad")
    private Integer cantidad;

    @Field("unidad")
    private String unidad;

    @Field("threshold_minimo")
    private Integer thresholdMinimo;

    @Field("created_at")
    private Instant createdAt;

    @Field("updated_at")
    private Instant updatedAt;

    public ItemInventarioDocument() {
    }
}