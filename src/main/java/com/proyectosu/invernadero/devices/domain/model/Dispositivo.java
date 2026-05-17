package com.proyectosu.invernadero.devices.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@AllArgsConstructor
public class Dispositivo {

    private String id;
    private String deviceId;
    private String nombre;
    private String tipo;
    private String estado;
    private List<String> sensores;
    private List<String> actuadores;
    private Instant createdAt;
}