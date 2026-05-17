package com.proyectosu.invernadero.inventory.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class RegistroConsumoRequest {

    @NotBlank(message = "El item_id es obligatorio")
    private String item_id;

    @NotBlank(message = "El crop_id es obligatorio")
    private String crop_id;

    @Positive(message = "La quantity debe ser mayor que cero")
    private BigDecimal quantity;

    @NotBlank(message = "La unidad es obligatoria")
    private String unit;

    @NotBlank(message = "El source es obligatorio")
    private String source;
}