package com.proyectosu.invernadero.rest.controller;

import com.proyectosu.invernadero.application.usecase.GuardarEventoUseCase;
import com.proyectosu.invernadero.application.usecase.ObtenerEventosUseCase;
import com.proyectosu.invernadero.infraestructure.persistencenorel.adapter.MqttPublisherAdapter;
import com.proyectosu.invernadero.rest.dto.request.EventoRequest;
import com.proyectosu.invernadero.rest.dto.response.EventoResponse;
import com.proyectosu.invernadero.rest.mapper.EventoResponseMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final GuardarEventoUseCase guardarEventoUseCase;
    private final MqttPublisherAdapter publisher;
    private final ObtenerEventosUseCase obtenerEventosUseCase;
    private final EventoResponseMapper eventoResponseMapper;

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
