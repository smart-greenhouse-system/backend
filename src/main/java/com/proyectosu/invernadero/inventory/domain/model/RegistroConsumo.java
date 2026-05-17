package com.proyectosu.invernadero.inventory.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@AllArgsConstructor
public class RegistroConsumo {

    private String id;
    private String itemId;
    private String cropId;
    private BigDecimal quantity;
    private String unit;
    private String source;
    private Instant createdAt;
}