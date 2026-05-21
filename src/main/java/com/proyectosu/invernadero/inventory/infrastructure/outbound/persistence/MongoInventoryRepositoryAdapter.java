package com.proyectosu.invernadero.inventory.infrastructure.outbound.persistence;

import com.proyectosu.invernadero.inventory.domain.model.InventoryItem;
import com.proyectosu.invernadero.inventory.domain.port.InventoryRepositoryPort;
import com.proyectosu.invernadero.inventory.infrastructure.outbound.mappers.InventoryPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MongoInventoryRepositoryAdapter implements InventoryRepositoryPort {

    private final MongoInventoryRepository mongoInventoryRepository;
    private final InventoryPersistenceMapper inventoryPersistenceMapper;

    @Override
    public List<InventoryItem> findAll() {
        return mongoInventoryRepository.findAll()
                .stream()
                .map(inventoryPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<InventoryItem> findById(String id) {
        return mongoInventoryRepository.findById(id)
                .map(inventoryPersistenceMapper::toDomain);
    }

    @Override
    public InventoryItem save(InventoryItem inventoryItem) {
        MongoInventoryItemDocument document = inventoryPersistenceMapper.toDocument(inventoryItem);
        MongoInventoryItemDocument savedDocument = mongoInventoryRepository.save(document);
        return inventoryPersistenceMapper.toDomain(savedDocument);
    }
}
