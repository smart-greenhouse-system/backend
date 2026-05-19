package com.proyectosu.invernadero.inventory.domain.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemInventario {

    private final String id;
    private final String nombre;
    private final Integer cantidad;
    private final String unidad;
    private final Integer thresholdMinimo;
    private final Instant createdAt;
    private final Instant updatedAt;

    public ItemInventario with(Integer cantidad, Integer thresholdMinimo, Instant updatedAt) {
        return new ItemInventario(
                id,
                nombre,
                cantidad,
                unidad,
                thresholdMinimo,
                createdAt,
                updatedAt
        );
    }
}