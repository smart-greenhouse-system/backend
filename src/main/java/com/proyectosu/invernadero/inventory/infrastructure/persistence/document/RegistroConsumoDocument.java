package com.proyectosu.invernadero.inventory.infrastructure.persistence.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.Instant;

@Document(collection = "inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroConsumoDocument {

    @Id
    private String id;

    @Field("item_id")
    private String itemId;

    @Field("crop_id")
    private String cropId;

    @Field("quantity")
    private BigDecimal quantity;

    @Field("unit")
    private String unit;

    @Field("source")
    private String source;

    @Field("created_at")
    private Instant createdAt;
}