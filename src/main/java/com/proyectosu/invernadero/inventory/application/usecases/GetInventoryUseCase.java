package com.proyectosu.invernadero.inventory.application.usecases;

import com.proyectosu.invernadero.inventory.domain.model.InventoryItem;
import com.proyectosu.invernadero.inventory.domain.port.InventoryRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class GetInventoryUseCase {

    private final InventoryRepositoryPort inventoryRepositoryPort;

    public List<InventoryItem> execute() {
        return inventoryRepositoryPort.findAll()
                .stream()
                .sorted(Comparator
                        .comparing(InventoryItem::getNombre, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER))
                        .thenComparing(InventoryItem::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(InventoryItem::getId, Comparator.nullsLast(String::compareTo)))
                .toList();
    }
}
