package com.proyectosu.invernadero.predictions.application.usecase;

import com.proyectosu.invernadero.predictions.domain.model.Prediccion;
import com.proyectosu.invernadero.predictions.domain.ports.PrediccionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ListarPrediccionesUseCase {

    private final PrediccionRepositoryPort repositoryPort;

    public List<Prediccion> ejecutar() {
        return repositoryPort.listarTodas();
    }
}