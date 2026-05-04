package com.proyectosu.invernadero.application.usecase;

import com.proyectosu.invernadero.domain.model.prueba.Evento;
import com.proyectosu.invernadero.domain.ports.EventoRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ObtenerEventosUseCase {

    private final EventoRepositoryPort eventoRepositoryPort;

    public List<Evento> ejecutar() {
        return eventoRepositoryPort.obtenerTodos();
    }
}
