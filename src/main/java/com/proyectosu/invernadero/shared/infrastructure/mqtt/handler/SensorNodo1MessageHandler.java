package com.proyectosu.invernadero.shared.infrastructure.mqtt.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
@Component
public class SensorNodo1MessageHandler {

    private final ObjectMapper objectMapper;

    public void handle(String topic, JsonNode payload){
        // Aquí debe ir:
        // payload → DTO
        // UseCase
        // lógica de comandos
    }
}
