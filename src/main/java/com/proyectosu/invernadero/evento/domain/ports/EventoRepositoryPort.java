package com.proyectosu.invernadero.evento.domain.ports;

import com.proyectosu.invernadero.evento.domain.Evento;

import java.util.List;

public interface EventoRepositoryPort {

    void guardar(Evento evento);

    List<Evento> obtenerTodos();
}
