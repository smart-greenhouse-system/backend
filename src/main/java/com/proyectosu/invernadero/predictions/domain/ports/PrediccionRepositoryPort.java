package com.proyectosu.invernadero.predictions.domain.ports;

import com.proyectosu.invernadero.predictions.domain.model.Prediccion;

import java.util.List;

public interface PrediccionRepositoryPort {

    List<Prediccion> listarTodas();
}