package com.proyectosu.invernadero.inventory.domain.model;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class InventoryItem {

    private final String id;
    private final String nombre;
    private final Integer cantidad;
    private final String unidad;
    private final Integer thresholdMinimo;
    private final Instant createdAt;

    private InventoryItem(
            String id,
            String nombre,
            Integer cantidad,
            String unidad,
            Integer thresholdMinimo,
            Instant createdAt
    ) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.thresholdMinimo = thresholdMinimo;
        this.createdAt = createdAt;
    }

    public static InventoryItem createNew(
            String nombre,
            Integer cantidad,
            String unidad,
            Integer thresholdMinimo
    ) {
        return new InventoryItem(
                UUID.randomUUID().toString(),
                nombre,
                cantidad,
                unidad,
                thresholdMinimo,
                Instant.now()
        );
    }

    public static InventoryItem restore(
            String id,
            String nombre,
            Integer cantidad,
            String unidad,
            Integer thresholdMinimo,
            Instant createdAt
    ) {
        return new InventoryItem(
                id,
                nombre,
                cantidad,
                unidad,
                thresholdMinimo,
                createdAt
        );
    }

    public InventoryItem applyPartialUpdate(Integer cantidad, Integer thresholdMinimo) {
        return InventoryItem.restore(
                this.id,
                this.nombre,
                cantidad != null ? cantidad : this.cantidad,
                this.unidad,
                thresholdMinimo != null ? thresholdMinimo : this.thresholdMinimo,
                this.createdAt
        );
    }
}
