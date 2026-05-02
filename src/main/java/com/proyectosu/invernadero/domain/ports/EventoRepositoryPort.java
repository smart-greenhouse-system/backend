package com.proyectosu.invernadero.domain.ports;

import com.proyectosu.invernadero.domain.model.prueba.RegistroEvento;

public interface RegistroEventoRepositoryPort {
    void guardar(RegistroEvento evento);
}
