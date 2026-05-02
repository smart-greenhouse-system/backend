package com.proyectosu.invernadero.rest.controller;

import com.proyectosu.invernadero.application.usecase.GuardarEventoUseCase;
import com.proyectosu.invernadero.infraestructure.persistencenorel.adapter.MqttPublisherAdapter;
import com.proyectosu.invernadero.rest.dto.request.EventoRequest;
import com.proyectosu.invernadero.rest.dto.response.EventoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final GuardarEventoUseCase guardarEventoUseCase;
    private final MqttPublisherAdapter publisher;

    @PostMapping
    public ResponseEntity<EventoResponse> guardar(
            @Valid @RequestBody EventoRequest request) {

        guardarEventoUseCase.ejecutar(
                request.getOrigen(),
                request.getTipo(),
                request.getMensaje()
        );

        publisher.publicar(
                "iot/control/evento",
                request.getMensaje()
        );

        EventoResponse response = new EventoResponse(
                "Evento guardado correctamente",
                "OK"
        );


        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
