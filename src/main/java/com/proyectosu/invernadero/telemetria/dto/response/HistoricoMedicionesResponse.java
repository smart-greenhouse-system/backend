package com.proyectosu.invernadero.telemetria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class HistoricoMedicionesResponse {

    private String device_id;
    private int count;
    private List<MedicionTelemetriaResponse> readings;
}