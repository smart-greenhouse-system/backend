package com.proyectosu.invernadero.evento.inbound.controller;

import com.proyectosu.invernadero.evento.application.usecase.GuardarEventoUseCase;
import com.proyectosu.invernadero.evento.application.usecase.ObtenerEventosUseCase;
import com.proyectosu.invernadero.evento.domain.Evento;
import com.proyectosu.invernadero.evento.inbound.dto.request.EventoRequest;
import com.proyectosu.invernadero.evento.inbound.dto.response.EventoResponse;
import com.proyectosu.invernadero.evento.inbound.mapper.EventoResponseMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final GuardarEventoUseCase guardarEventoUseCase;
    private final ObtenerEventosUseCase obtenerEventosUseCase;
    private final EventoResponseMapper eventoResponseMapper;

    @PostMapping
    public ResponseEntity<EventoResponse> guardar(
            @Valid @RequestBody EventoRequest request) {

        Evento evento = guardarEventoUseCase.ejecutar(
                request.getOrigen(),
                request.getTipo(),
                request.getMensaje()
                );

        return ResponseEntity.status(HttpStatus.CREATED).body(eventoResponseMapper.toResponse(evento));
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
