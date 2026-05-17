package com.proyectosu.invernadero.devices.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@AllArgsConstructor
public class DispositivoResponse {

    private String device_id;
    private String nombre;
    private String tipo;
    private String estado;
    private List<String> sensores;
    private List<String> actuadores;
    private Instant created_at;
}