package com.proyectosu.invernadero.evento.application.usecase;

import com.proyectosu.invernadero.evento.domain.Evento;
import com.proyectosu.invernadero.evento.domain.ports.EventoRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ObtenerEventosUseCase {

    private final EventoRepositoryPort eventoRepositoryPort;

    public List<Evento> ejecutar() {
        return eventoRepositoryPort.obtenerTodos();
    }
}
