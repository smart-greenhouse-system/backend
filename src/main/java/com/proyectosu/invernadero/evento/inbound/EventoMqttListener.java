package com.proyectosu.invernadero.rest.mqtt;

import com.proyectosu.invernadero.evento.application.usecase.GuardarEventoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventoMqttListener {

    private final GuardarEventoUseCase guardarEventoUseCase;

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void recibirMensaje(Message<?> message) {

        String payload = message.getPayload().toString();

        String topic = message.getHeaders()
                .get("mqtt_receivedTopic")
                .toString();

        if (topic.startsWith("iot/sensores")) {

            guardarEventoUseCase.ejecutar(
                    "MQTT",
                    "SENSOR",
                    payload
            );
        }

        else if (topic.startsWith("iot/estado")) {

            guardarEventoUseCase.ejecutar(
                    "MQTT",
                    "ESTADO",
                    payload
            );
        }

        System.out.println("Topic: " + topic);
        System.out.println("Payload: " + payload);
    }
}
