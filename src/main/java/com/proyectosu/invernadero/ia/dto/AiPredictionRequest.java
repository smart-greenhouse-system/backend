package com.proyectosu.invernadero.ia.dto;

import jakarta.validation.constraints.NotBlank;

public record AiPredictionRequest(
        @NotBlank(message = "La imagen en base64 es obligatoria")
        String image,

        String deviceId
) {
}
