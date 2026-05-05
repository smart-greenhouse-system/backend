package com.proyectosu.invernadero.auth.domain.ports;

import com.proyectosu.invernadero.evento.domain.Evento;

import java.util.List;

public interface EventoRepositoryPort {

    void guardar(Evento evento);

    List<Evento> obtenerTodos();
}
