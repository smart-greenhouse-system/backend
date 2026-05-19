package com.proyectosu.invernadero.predictions.infrastructure.persistence.adapter;

import com.proyectosu.invernadero.predictions.domain.model.Prediccion;
import com.proyectosu.invernadero.predictions.domain.ports.PrediccionRepositoryPort;
import com.proyectosu.invernadero.predictions.infrastructure.persistence.mapper.PrediccionMapper;
import com.proyectosu.invernadero.predictions.infrastructure.persistence.repository.PrediccionMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PrediccionRepositoryAdapter implements PrediccionRepositoryPort {

    private final PrediccionMongoRepository repository;
    private final PrediccionMapper mapper;

    @Override
    public List<Prediccion> listarTodas() {
        return repository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}