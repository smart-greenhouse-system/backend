package com.proyectosu.invernadero.shared.infrastructure.mqtt.handler;

import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

@Component
public class SensorNodo2MessageHandler {

    public void handle(String topic, JsonNode payload){
        // Aquí debe ir:
        // payload → DTO
        // UseCase
        // lógica de comandos
    }
}
