package com.proyectosu.invernadero.auth.application.usecase;

import com.proyectosu.invernadero.auth.domain.model.prueba.Evento;
import com.proyectosu.invernadero.auth.domain.ports.EventoRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ObtenerEventosUseCase {

    private final EventoRepositoryPort eventoRepositoryPort;

    public List<Evento> ejecutar() {
        return eventoRepositoryPort.obtenerTodos();
    }
}
