package com.proyectosu.invernadero.inventory.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class InventoryItemResponse {

    private String id;

    private String nombre;

    private Integer cantidad;

    private String unidad;

    @JsonProperty("threshold_minimo")
    private Integer thresholdMinimo;

    @JsonProperty("created_at")
    private Instant createdAt;
}
