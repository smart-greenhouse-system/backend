package com.proyectosu.invernadero.shared.infrastructure.mqtt.handler;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

@Component
public class SensorNodo2MessageHandler {

    public void handle(String topic, JsonNode payload){
        // Aquí debe ir:
        // payload → DTO
        // UseCase
        // lógica de comandos
    }
}
