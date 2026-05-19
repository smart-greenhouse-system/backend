package com.proyectosu.invernadero.inventory.infrastructure.persistence.adapter;

import com.proyectosu.invernadero.inventory.domain.model.ItemInventario;
import com.proyectosu.invernadero.inventory.domain.ports.ItemInventarioRepositoryPort;
import com.proyectosu.invernadero.inventory.infrastructure.persistence.mapper.ItemInventarioMapper;
import com.proyectosu.invernadero.inventory.infrastructure.persistence.repository.ItemInventarioMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ItemInventarioRepositoryAdapter implements ItemInventarioRepositoryPort {

    private final ItemInventarioMongoRepository repository;
    private final ItemInventarioMapper mapper;

    @Override
    public List<ItemInventario> listarTodos() {
        return repository.findAllByOrderByNombreAsc()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<ItemInventario> buscarPorId(String id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public ItemInventario guardar(ItemInventario itemInventario) {
        return mapper.toDomain(repository.save(mapper.toDocument(itemInventario)));
    }
}