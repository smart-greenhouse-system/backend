package com.proyectosu.invernadero.mqtt.infrastructure.dto;

public record ActuatorCommandDto(
        String comando,
        Object valor
) {
}
