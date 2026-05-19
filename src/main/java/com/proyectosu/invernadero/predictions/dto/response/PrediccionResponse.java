package com.proyectosu.invernadero.predictions.dto.response;

import com.proyectosu.invernadero.predictions.domain.model.Prediccion;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PrediccionResponse {

    private final String id;
    private final String tipo;
    private final String mensaje;
    private final String accion_recomendada;
    private final String device_id;
    private final String created_at;

    public static PrediccionResponse fromDomain(Prediccion prediccion) {
        return new PrediccionResponse(
                prediccion.getId(),
                prediccion.getTipo(),
                prediccion.getMensaje(),
                prediccion.getAccionRecomendada(),
                prediccion.getDeviceId(),
                prediccion.getCreatedAt() == null ? null : prediccion.getCreatedAt().toString()
        );
    }
}