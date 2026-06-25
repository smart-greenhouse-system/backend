package com.proyectosu.invernadero.inventory.application.usecases;

import com.proyectosu.invernadero.inventory.application.command.CreateInventoryItemCommand;
import com.proyectosu.invernadero.inventory.domain.model.InventoryItem;
import com.proyectosu.invernadero.inventory.domain.port.InventoryRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateInventoryItemUseCase {

    private final InventoryRepositoryPort inventoryRepositoryPort;

    public InventoryItem execute(CreateInventoryItemCommand command) {
        validateCommand(command);

        InventoryItem inventoryItem = InventoryItem.createNew(
                command.getNombre(),
                command.getCantidad(),
                command.getUnidad(),
                command.getThresholdMinimo()
        );

        return inventoryRepositoryPort.save(inventoryItem);
    }

    private void validateCommand(CreateInventoryItemCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("El comando de inventario no puede ser nulo");
        }

        if (isBlank(command.getNombre())) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }

        if (command.getCantidad() == null) {
            throw new IllegalArgumentException("La cantidad es obligatoria");
        }

        if (command.getCantidad() < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }

        if (isBlank(command.getUnidad())) {
            throw new IllegalArgumentException("La unidad es obligatoria");
        }

        if (command.getThresholdMinimo() == null) {
            throw new IllegalArgumentException("El threshold mínimo es obligatorio");
        }

        if (command.getThresholdMinimo() < 0) {
            throw new IllegalArgumentException("El threshold mínimo no puede ser negativo");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
