package com.proyectosu.invernadero.inventory.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemInventarioCreateRequest {

    @NotBlank(message = "nombre es obligatorio")
    private String nombre;

    @NotNull(message = "cantidad es obligatoria")
    @Min(value = 0, message = "cantidad debe ser mayor o igual que cero")
    private Integer cantidad;

    @NotBlank(message = "unidad es obligatoria")
    private String unidad;

    @NotNull(message = "threshold_minimo es obligatorio")
    @Min(value = 0, message = "threshold_minimo debe ser mayor o igual que cero")
    private Integer threshold_minimo;
}