package com.proyectosu.invernadero.evento.inbound;

import com.proyectosu.invernadero.evento.application.usecase.GuardarEventoUseCase;
import com.proyectosu.invernadero.evento.application.usecase.ObtenerEventosUseCase;
import com.proyectosu.invernadero.evento.infrastructure.mqtt.MqttPublisherAdapter;
import com.proyectosu.invernadero.evento.dto.request.EventoRequest;
import com.proyectosu.invernadero.evento.dto.response.EventoResponse;
import com.proyectosu.invernadero.evento.infrastructure.mqtt.EventoMqttMapper;
import com.proyectosu.invernadero.evento.infrastructure.persistence.mapper.EventoResponseMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final GuardarEventoUseCase guardarEventoUseCase;
    private final ObtenerEventosUseCase obtenerEventosUseCase;
    private final EventoResponseMapper eventoResponseMapper;
    private final MqttPublisherAdapter publisher;
    private final EventoMqttMapper mqttMapper;
    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<EventoResponse> guardar(
            @Valid @RequestBody EventoRequest request) {

        guardarEventoUseCase.ejecutar(
                request.getOrigen(),
                request.getTipo(),
                request.getMensaje()
        );

        try {
            var payloadObj = mqttMapper.toPayload(request.getMensaje());

            String json = objectMapper.writeValueAsString(payloadObj);

            publisher.publicar("iot/control/evento", json);

        } catch (Exception e) {
            throw new RuntimeException("Error convirtiendo payload MQTT");
        }

        EventoResponse response = new EventoResponse(
                request.getOrigen(),
                request.getTipo(),
                request.getMensaje()
        );


        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<EventoResponse>> obtenerTodos() {

        List<EventoResponse> response =
                eventoResponseMapper.toResponseList(
                        obtenerEventosUseCase.ejecutar()
                );

        return ResponseEntity.ok(response);
    }

}
