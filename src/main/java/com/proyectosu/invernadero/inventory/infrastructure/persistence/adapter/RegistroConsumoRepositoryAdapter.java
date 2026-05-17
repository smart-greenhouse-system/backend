package com.proyectosu.invernadero.inventory.infrastructure.persistence.adapter;

import com.proyectosu.invernadero.inventory.domain.model.RegistroConsumo;
import com.proyectosu.invernadero.inventory.domain.ports.RegistroConsumoRepositoryPort;
import com.proyectosu.invernadero.inventory.infrastructure.persistence.document.RegistroConsumoDocument;
import com.proyectosu.invernadero.inventory.infrastructure.persistence.mapper.RegistroConsumoMapper;
import com.proyectosu.invernadero.inventory.infrastructure.persistence.repository.RegistroConsumoMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistroConsumoRepositoryAdapter implements RegistroConsumoRepositoryPort {

    private final RegistroConsumoMongoRepository repository;
    private final RegistroConsumoMapper mapper;

    @Override
    public RegistroConsumo guardar(RegistroConsumo registroConsumo) {
        RegistroConsumoDocument document = repository.save(mapper.toDocument(registroConsumo));
        return mapper.toDomain(document);
    }
}