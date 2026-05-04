package com.proyectosu.invernadero.domain.ports;

import com.proyectosu.invernadero.domain.model.prueba.Evento;

import java.util.List;

public interface EventoRepositoryPort {

    void guardar(Evento evento);

    List<Evento> obtenerTodos();
}
