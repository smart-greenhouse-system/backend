package com.proyectosu.invernadero.inventory.application.usecases;

import com.proyectosu.invernadero.inventory.application.command.UpdateInventoryItemCommand;
import com.proyectosu.invernadero.inventory.domain.model.InventoryItem;
import com.proyectosu.invernadero.inventory.domain.port.InventoryRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateInventoryItemUseCase {

    private final InventoryRepositoryPort inventoryRepositoryPort;

    public InventoryItem execute(UpdateInventoryItemCommand command) {
        validateCommand(command);

        InventoryItem currentItem = inventoryRepositoryPort.findById(command.getId())
                .orElseThrow(() -> new IllegalArgumentException("Item de inventario no encontrado"));

        InventoryItem updatedItem = currentItem.applyPartialUpdate(
                command.getCantidad(),
                command.getThresholdMinimo()
        );

        return inventoryRepositoryPort.save(updatedItem);
    }

    private void validateCommand(UpdateInventoryItemCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("El comando de inventario no puede ser nulo");
        }

        if (isBlank(command.getId())) {
            throw new IllegalArgumentException("El id es obligatorio");
        }

        if (command.getCantidad() == null && command.getThresholdMinimo() == null) {
            throw new IllegalArgumentException("Debe enviar al menos un campo para actualizar");
        }

        if (command.getCantidad() != null && command.getCantidad() < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }

        if (command.getThresholdMinimo() != null && command.getThresholdMinimo() < 0) {
            throw new IllegalArgumentException("El threshold mínimo no puede ser negativo");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
