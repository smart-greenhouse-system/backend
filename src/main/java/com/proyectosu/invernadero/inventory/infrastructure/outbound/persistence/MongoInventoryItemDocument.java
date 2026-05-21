package com.proyectosu.invernadero.inventory.infrastructure.outbound.persistence;

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
@Document(collection = "inventory")
public class MongoInventoryItemDocument {

    @Id
    private String id;

    private String nombre;

    private Integer cantidad;

    private String unidad;

    @Field("threshold_minimo")
    private Integer thresholdMinimo;

    @Field("created_at")
    private Instant createdAt;
}
