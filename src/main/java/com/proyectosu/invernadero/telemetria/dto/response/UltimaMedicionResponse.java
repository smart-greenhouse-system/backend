package com.proyectosu.invernadero.telemetria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UltimaMedicionResponse {

    private String device_id;
    private MedicionTelemetriaResponse latest;
}