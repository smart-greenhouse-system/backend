package com.proyectosu.invernadero.shared.infrastructure.mqtt.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
