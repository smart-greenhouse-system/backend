package com.proyectosu.invernadero.domain.ports;

import com.proyectosu.invernadero.domain.model.prueba.Evento;

public interface EventoRepositoryPort {
    void guardar(Evento evento);
}
