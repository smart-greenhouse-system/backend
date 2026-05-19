package com.proyectosu.invernadero.inventory.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemInventarioUpdateRequest {

    @Min(value = 0, message = "cantidad debe ser mayor o igual que cero")
    private Integer cantidad;

    @Min(value = 0, message = "threshold_minimo debe ser mayor o igual que cero")
    private Integer threshold_minimo;
}