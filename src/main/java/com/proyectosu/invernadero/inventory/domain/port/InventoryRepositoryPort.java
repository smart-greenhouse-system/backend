package com.proyectosu.invernadero.inventory.domain.port;

import com.proyectosu.invernadero.inventory.domain.model.InventoryItem;

import java.util.List;
import java.util.Optional;

public interface InventoryRepositoryPort {

    List<InventoryItem> findAll();

    Optional<InventoryItem> findById(String id);

    InventoryItem save(InventoryItem inventoryItem);
}
